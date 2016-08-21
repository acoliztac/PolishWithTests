import java.util.ArrayList;

import static java.lang.Character.isDigit;
import static java.lang.Math.exp;
import static java.lang.Math.pow;

/**
 * Created by Никита on 21.08.2016.
 */
public class Polish {
    public String answer;

    public String getAnswer() {
        return answer;
    }

    public Polish(String expr) {
        ArrayList<String> StringToPolishArray = converterToPolish(expr);
        for (int i = 0; i < StringToPolishArray.size(); i++) {
            try {
                double tmp = Double.parseDouble(StringToPolishArray.get(i));
            } catch (NumberFormatException e) {
                try {
                    decision(StringToPolishArray, i);
                } catch (Exception e1) {
                    System.out.println("В выражении присутствует буквенный символ");
                    System.exit(0);
                }
                i -= 2;
            }
        }
        String fResult = String.format("%.3f", Double.parseDouble(StringToPolishArray.get(0)));
        answer = fResult;
        System.out.println("\tРезультат = " + fResult);
    }

    private static void decision(ArrayList<String> polish, int i) {
        if (polish.size() < 2){
            return;
        }
        double first = Double.parseDouble(polish.get(i - 2));
        double second = Double.parseDouble(polish.get(i - 1));
        if (polish.get(i).equals("+")){
            polish.set(i - 2, String.valueOf(first + second));
        } else if (polish.get(i).equals("-")){
            polish.set(i - 2, String.valueOf(first - second));
        } else if (polish.get(i).equals("*")){
            polish.set(i - 2, String.valueOf(first * second));
        } else if (polish.get(i).equals("/")){
            polish.set(i - 2, String.valueOf(first / second));
        } else if (polish.get(i).equals("^")){
            polish.set(i - 2, String.valueOf(pow(first, second)));
        }
        polish.remove(i);
        polish.remove(i - 1);
    }

    private static ArrayList<String> converterToPolish(String text) {
        text = text.replaceAll(" ", "");
        text = text.replaceAll(",", ".");
        System.out.println(text + ": форматированное исходное выражение");

        ArrayList<String> result = mainInitialization(text);

        for (String s : result){
            System.out.print(s + " ");
        }
        System.out.println(": форматированная польская запись");
        return result;
    }

    private static ArrayList<String> mainInitialization(String text) {
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<Character> symbols = new ArrayList<Character>();
        ArrayList<Integer> importance = new ArrayList<Integer>();
        Character[] chars = {'+', '-', '*', '/', '^', '(', ')'};

        for (int i = 0; i < text.length(); i++) {
            StringBuilder sb = new StringBuilder();
            if (isDigit(text.charAt(i))){
                for (int j = i; j < text.length(); j++) {
                    i = j;
                    char ch = text.charAt(j);
                    if (isDigit(ch)){
                        sb.append(ch);
                    } else if (ch == '.' || ch == ',' ) {
                        sb.append('.');
                    } else {
                        break;
                    }
                }
                result.add(String.valueOf(sb));
                if (i == text.length() - 1 && isDigit(text.charAt(i))){
                    break;
                }
                initializeSymb(text, symbols, importance, i);
            } else {
                initializeSymb(text, symbols, importance, i);
            }

            initializeResult(result, symbols, importance);

        }

        lastNumberControl(result);

        lastBraceletsControl(importance);

        for (int i = symbols.size() - 1; i >= 0; i--) {
            result.add(String.valueOf(symbols.get(i)));
        }

        return result;
    }

    private static void lastNumberControl(ArrayList<String> result) {
        if (result.size() == 0){
            System.out.println("Вы не ввели ни одного числа.");
            System.exit(0);
        }
    }

    private static void lastBraceletsControl(ArrayList<Integer> importance) {
        int countBracelets = 0;
        for (int i : importance){
            if (i == 4)
                countBracelets++;
            if (i == 5)
                countBracelets--;
        }
        if (countBracelets != 0){
            System.out.println("Переизбыток " + (countBracelets > 0 ? "открывающих" : "закрывающих") + " скобок.");
            System.exit(0);
        }
    }

    private static void initializeResult(ArrayList<String> result, ArrayList<Character> symbols, ArrayList<Integer> importance) {
        while (true){
            if (importance.size() < 2){
                break;
            }

            int preLast = importance.get(importance.size() - 2);
            int last = importance.get(importance.size() - 1);

            if (preLast == 4 && !(last == 5)) {
                break;
            } else if (preLast == 4 && last == 5) {
                importance.remove(importance.size() - 1);
                importance.remove(importance.size() - 1);
                symbols.remove(symbols.size() - 1);
                symbols.remove(symbols.size() - 1);
            } else if (!(preLast < last) || last == 5) {
                char tmp = symbols.get(symbols.size() - 2);
                symbols.remove(symbols.size() - 2);
                importance.remove(importance.size() - 2);
                result.add(String.valueOf(tmp));
            } else {
                break;
            }
        }
    }

    private static void initializeSymb(String text, ArrayList<Character> symbols, ArrayList<Integer> importance, int i) {
        int imp = 0;
        if (isDigit(text.charAt(i))){
            return;
        }
        switch (text.charAt(i)){
            case '=':
                imp = 0;
                break;
            case '+':
            case '-':
                imp = 1;
                break;
            case '/':
            case '*':
                imp = 2;
                break;
            case '^':
                imp = 3;
                break;
            case '(':
                imp = 4;
                break;
            case ')':
                imp = 5;
                break;
        }
        importance.add(imp);
        symbols.add(text.charAt(i));
    }
}
