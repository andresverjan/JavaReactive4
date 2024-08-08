public class reverse {
    public String reverseText() {
        String[] text = {"h", "e", "l", "l", "o"};
        StringBuilder textReverse = new StringBuilder();
        for (int i = (text.length - 1); i >= 0; i--) {
            textReverse.append(text[i]);
        }
        return textReverse.toString();
    }
}
