// demonstrasi penggunaan parser
import java.io.*;

class ParserDemo {
    public static void main ( String args[] ) throws IOException {
        String expr;

        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );

        Parser p = new Parser();

        System.out.println( "Masukkan ekspresi kosong untuk berhenti." );

        for (;;) {
            System.out.print("Masukkan Ekspresi: ");
            expr = br.readLine();

            if ( expr.equals("") ) {
                break;
            }

            try {
                System.out.println( "Hasil: " + p.evaluate( expr ) );
                System.out.println();

            } catch ( ParserException exc ) {
                System.out.println( exc );
            }
        }

    } // akhir dari method main

} // akhir dari class ParserDemo
