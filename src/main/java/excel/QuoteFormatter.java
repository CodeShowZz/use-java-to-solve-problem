package excel;

    public class QuoteFormatter implements Formatter {
    @Override
    public String format(String data) {
        return "'" + data + "'";
    }
}
