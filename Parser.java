/**
 * Class Parser adalah recursive descent parser yang tidak menggunakan variabel
 *
 * @creator mujib <mujib.programmer@gmail.com>
 */
class Parser {
    // ini adalah tipe-tipe token
    final int NONE = 0;
    final int DELIMITER = 1;
    final int VARIABLE = 2;
    final int NUMBER = 3;

    // ini adalah tipe-tipe sintaks error
    final int SYNTAX = 0;
    final int UNBALPARENS = 1;
    final int NOEXP = 2;
    final int DIVBYZERO = 3;

    // token ini mengindikasikan akhir-dari-ekspresi
    final String EOE = "\0";

    private String exp;         // mengacu pada string ekspresi
    private int expIdx;         // index saat ini pada ekspresi
    private String token;       // menyimpan token saat ini
    private int tokType;        // menyimpan tipe token

    // titik masuk parser
    public double evaluate( String expstr) throws ParserException {
        double result;
        exp = expstr;
        expIdx = 0;

        getToken();
        if ( token.equals( EOE )) {
            handleErr( NOEXP ); // tidak ada ekspresi yang ditemukan
        }

        // urai dan evaluasi ekspresi
        result = evalExp2();

        if (! token.equals( EOE )) {    // token terakhir harus EOE
            handleErr( SYNTAX );
        }

        return result;

    } // akhir dari method evaluate


    /**
     * tambahkan atau kurangi 2 term.
     */
    private double evalExp2() throws ParserException {
        char op;
        double result;
        double partialResult;

        result = evalExp3();

        while ( ( op = token.charAt( 0 ) ) == '+' || op == '-' ) {
            getToken();
            partialResult = evalExp3();

            switch ( op ) {
                case '-':
                    result = result - partialResult;
                    break;
                case '+':
                    result = result + partialResult;
                    break;
            }
        }

        return result;

    } // akhir dari method evalExp2

    /**
     * kalikan atau bagikan 2 faktor
     */
    private double evalExp3() throws ParserException {
        char op;
        double result;
        double partialResult;

        result = evalExp4();

        while ( (op = token.charAt(0) ) == '*' || op == '/' || op == '%' ) {
            getToken();
            partialResult = evalExp4();

            switch (op) {
                case '*':
                    result = result * partialResult;
                    break;
                case '/':
                    
                    if ( partialResult == 0.0 ) {
                        handleErr( DIVBYZERO );
                    }

                    result = result / partialResult;
                    break;
                case '%':
                    if ( partialResult == 0.0 ) {
                        handleErr( DIVBYZERO );
                    }

                    result = result % partialResult;
                    break;
            }

        }

        return result;

    } // akhir dari method evalExp3

    /**
     * proses perpangkatan
     **/
    private double evalExp4() throws ParserException {
        double result;
        double partialResult;
        double ex;
        int t;

        result = evalExp5();
        
        if ( token.equals("^") ) {
            getToken();

            partialResult = evalExp4();
            ex =  result;

            if ( partialResult == 0.0 ) {
                result = 1.0;

            }
            else {
                for ( t = (int) partialResult - 1; t > 0; t-- ) {
                    result = result * ex;
                }
            }

        }

        return result;

    } // akhir dari method evalExp5 

    /**
     * mengevaluasi unary + atau -
     */
    private double evalExp5() throws ParserException {
        double result;
        String op;

        op = "";
        if ( ( tokType == DELIMITER ) && token.equals("+") || token.equals("-") ) {
            op = token;
            getToken();
        }

        result = evalExp6();

        if ( op.equals("-") ) {
            result = -result;
        }

        return result;

    } // akhir dari method evalExp5

    /**
     * proses expresi dalam kurum
     */
    private double evalExp6() throws ParserException {
        double result;

        if ( token.equals( "(" ) ) {
            getToken();

            result = evalExp2();

            if ( ! token.equals(")") ) {
                handleErr( UNBALPARENS );
            }

            getToken();
        }
        else {
            result = atom();
        }

        return result;

    } // akhir dari method evalExp6

    /**
     * mendapatkan nilai dari angka.
     */
    private double atom() throws ParserException {
        double result = 0.0;

        switch ( tokType ) {
            case NUMBER:
                try {
                    result = Double.parseDouble( token );
                } catch ( NumberFormatException exc ) {
                    handleErr( SYNTAX );
                }

                getToken();
                break;
            default:
                handleErr( SYNTAX );
                break;
        }

        return result;

    } // akhir dari method atom

    /**
     * Menangani error
     */
    private void handleErr( int error ) throws ParserException {
        String[] err = {
            "Syntax Error",
            "Unbalanced Parentheses",
            "No Expression Present",
            "Division by Zero"
        };

        throw new ParserException( err[ error ] );
    } // akhir dari method handleErr

    /**
     * memperoleh token berikutnya
     *
     */
    private void getToken() {
        tokType = NONE;
        token = "";

        // cek untuk akhir ekspresi
        if ( expIdx == exp.length() ) {
            token = EOE;
            return;
        }

        // lewati white space
        while ( expIdx < exp.length() && Character.isWhitespace( exp.charAt( expIdx )  ) ) {
            ++expIdx;
        }
        
        // Trailing whitespace ends expression
        if ( expIdx == exp.length() ) {
            token = EOE;
            return;
        }

        if ( isDelim( exp.charAt( expIdx ) ) ) { // adalah operator
            token += exp.charAt( expIdx );
            expIdx++;
            tokType = DELIMITER;
        }
        else if ( Character.isLetter( exp.charAt( expIdx ) ) ) { // adalah variabel
            while ( ! isDelim( exp.charAt( expIdx ) ) ) {
                token += exp.charAt( expIdx );
                expIdx++;
                if ( expIdx >= exp.length() ) {
                    break;
                }
            }

            tokType = VARIABLE;
        } 
        else if ( Character.isDigit( exp.charAt( expIdx ) ) ) { // adalah angka
            while ( ! isDelim( exp.charAt( expIdx ) ) ) {
                token += exp.charAt( expIdx );
                expIdx++;
                if ( expIdx >= exp.length() ) {
                    break;
                }
            }

            tokType = NUMBER;

        } 
        else { // ekspresi diakhiri dengan karakter yang tak dikenal
            token = EOE;
            return;
        }

    } // akhir dari method getToken

    /**
     * Mengecek apakah karakter yang dilewatkan adalah delimiter
     * @return true jika  c adalah delimiter
     * @param c adalah karakter yang diperiksa
     *
     **/
    private boolean isDelim( char c ) {
        
        // return true jika c berisi salah satu dari karakter berikut
        if( (" +-/*%^=()".indexOf( c )  != -1 ) ) { 
            return true;
        }
        // selain itu return false
        else {
            return false;
        }
    } // akhir dari method isDelim

} // akhir dari class Parser
