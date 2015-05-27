package cn.young.algo;

import cn.young.ds.LinkedStack;

/**
 * Created by SevenYoung on 15-5-27.
 */
//This class used for :
// (1)evaluating the value of infix expression;
// (2)transforming the infix expression to postfix expression and prefix expression

//In fact , this class can be used for constructing a simple computer, ah!
public class Expression {
    //The precedence of the operator,this precedence preserved by the operator stack
//        '+' '-' '*' '/' '(' ')' '#'
//    '+'  >   >   <   <   <   >   >
//    '-'  >   >   <   <   <   >   >
//    '*'  >   >   >   >   <   >   >
//    '/'  >   >   >   >   <   >   >
//    '('  <   <   <   <   <   =
//    ')'  >   >   >   >       >   >
//    '#'  <   <   <   <   <       =
    //"#" represents the starting and the ending
    private String[] operatorsPermitted = {"+", "-", "*", "/", "(", ")", "#"};
    //Check the incoming expression's type
    private boolean isOperator(String exp){
        for(int i = 0; i < operatorsPermitted.length ; i++){
            if(exp.equalsIgnoreCase(operatorsPermitted[i])) return true;
        }
        return false;
    }
    //Get the index of this operator for precedence search
    private int index(String exp){
        for(int i = 0; i < operatorsPermitted.length ; i++){
            if(exp.equalsIgnoreCase(operatorsPermitted[i])) return i;
        }
        return -1;
    }

    //The precedence between the diff operators
    //The stack promise that the higher operator always in the top, never override by the lower precedence operator
    private char[][] precedence= {
        {'>', '>', '<', '<', '<', '>', '>'},
        {'>', '>', '<', '<', '<', '>', '>'},
        {'>', '>', '>', '>', '<', '>', '>'},
        {'>', '>', '>', '>', '<', '>', '>'},
        {'<', '<', '<', '<', '<', '=', ' '},
        {'>', '>', '>', '>', ' ', '>', '>'},
        {'<', '<', '<', '<', '<', ' ', '='}
    };

    //Compare the precedence of incoming operator with the top operator in stack
    private char compareRes(String top,String incoming){
        return precedence[index(top)][index(incoming)];
    }



    private String expression;

    //split the elements of expression consists of values and operators
    private String[] elems;

    private LinkedStack<String> operators = new LinkedStack<>();
    private LinkedStack<Integer> operands = new LinkedStack<>();

    //Resovle the expression to the String Array. eg: (12+3)/5-5 => {"(","12","+","3",")","/","5","-","5"}
    public String[] resolve(String exp){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < exp.length(); ){
            if(!Character.isDigit(exp.charAt(i))){
                //If this element is a operator, then it is a individual element," " for later split
                sb.append(exp.charAt(i)).append(" ");
                i++;
            }else{
                //If this element is a digit, then we check whether the next element is a digit
                StringBuilder tmp = new StringBuilder();
                tmp.append(exp.charAt(i));
                for(int j = i + 1; j < exp.length(); j++){
                    if(Character.isDigit(exp.charAt(j))){tmp.append(exp.charAt(j));}
                    else break;
                }
                sb.append(tmp.toString()).append(" ");//add this digit, " " for later split
                i += tmp.length();
            }
        }
        return sb.toString().split(" ");
    }

    //exp is ended with "#"
    public Expression(String exp){
        expression = exp;
        elems = resolve(expression);
        operators.push("#");
    }

    public int evaluate(){
        for(String item: elems){
            String top = operators.peek();
            if(!isOperator(item)) operands.push(Integer.parseInt(item));
            else if(compareRes(top,item) == '<') operators.push(item);
            else if(compareRes(top,item) == '>') {
                //Encounter a low priority operator , need to be checked many times
                while(compareRes(operators.peek(),item) == '>') {
                    String curOperator = operators.pop();
                    switch (curOperator) {
                        case "+": {
                            int operand2 = operands.pop();
                            int operand1 = operands.pop();
                            operands.push(operand1 + operand2);
                            break;
                        }
                        case "-": {
                            int operand2 = operands.pop();
                            int operand1 = operands.pop();
                            operands.push(operand1 - operand2);
                            break;
                        }
                        case "*": {
                            int operand2 = operands.pop();
                            int operand1 = operands.pop();
                            operands.push(operand1 * operand2);
                            break;
                        }
                        case "/": {
                            int operand2 = operands.pop();
                            int operand1 = operands.pop();
                            if (operand2 == 0) {
                                throw new IllegalArgumentException("The expression is invalid");
                            }
                            operands.push(operand1 / operand2);
                            break;
                        }
                    }
                }

                //When all the high precedence operators pushed before have been operated, then we may encounter the equal or high precedence incoming operator
                //operators.peek().equalsIgnoreCase("(")  is not the same as operators.peek() == "(" !!!!!
                if(compareRes(operators.peek(),item) == '=' && operators.peek().equalsIgnoreCase("(") && item.equalsIgnoreCase(")")) operators.pop();
                else if(compareRes(operators.peek(),item) == '<') operators.push(item);
            }
            else if(compareRes(top,item) == '=' && top.equalsIgnoreCase("#") && item.equalsIgnoreCase("#")) operators.pop();
        }
        return operands.pop();
    }

    //main for testing
    public static void main(String[] args) {
        Expression exp = new Expression("(12+3)/5-1#");
        for(String i: exp.resolve("(12+3)/5-1#")){
            System.out.println(i);
        }
        System.out.println(exp.evaluate());

    }
}
