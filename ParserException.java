// class eksepsi untuk parser error
class ParserException extends Exception {
    String errStr;      // menjelaskan pesan eror

    public ParserException( String str ) {
        errStr = str;
    } // akhir konstruktor

    public String toString() {
        return errStr;
    } // akhir method toString

} // akhir class ParserException
