package com.daniel99j.djutil.maths;

import com.daniel99j.djutil.ValueHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class MathsInterpreter {
    public static double eval(String s) {
        //create nodes
        //resolve nodes


        //if it reaches a +-*/ or end read past numbers

        Equation nodes = new Equation();
        Equation currentlyEditing = nodes;
        //ValueHolder<Resolvable> currentlyChanging = nodes.one;

        String reader = s;
        String currentNumber = "";
        int startCurrent = 0;
        int current = 0;
        while (!reader.isEmpty()) {
            if(reader.length() == current) {
                currentlyEditing.one.object = new Value(Double.parseDouble(currentNumber));
                break;
            }
            if(reader.charAt(current) == '+') {
                currentlyEditing.one.object = new Value(Double.parseDouble(currentNumber));
                currentNumber = "";
                Equation newE = new Equation();
                currentlyEditing.two.object = newE;
                currentlyEditing = newE;
            } else {
                currentNumber += reader.charAt(current);
            }
            current++;
        }

//        Equation e = new Equation();
//        top:
//        while(true) {
//            //read until operation
//            String remaining = s;
//            StringBuilder current = new StringBuilder();
//            while(!remaining.isEmpty()) {
//                if (!remaining.startsWith("+")) {
//                    current.append(s.charAt(0));
//                    remaining = remaining.substring(1);
//                } else {
//                    if(e.one == null) {
//                        Value v = new Value();
//                        v.value = Double.parseDouble(current.toString());
//                        e.one = v;
//                        break;
//                    } else if(e.two == null) {
//                        Value v = new Value();
//                        v.value = Double.parseDouble(current.toString());
//                        e.two = v;
//                        break top;
//                    }
//                }
//            }
//        }
//        return e.resolve();
        nodes.resolve();
        return nodes.getValue();
    }

    private static class Value extends Resolvable {
        protected final double value;

        public Value(double v) {
            this.value = v;
        }

        @Override
        public void resolve() {
            this.resolved = value;
        }

        @Override
        public double getValue() {
            return value;
        }
    }

    protected static class Resolvable {
        protected double resolved = Double.NaN;

        public void resolve() {
        }

        protected double getValue() {
            if(Double.isNaN(resolved)) throw new IllegalArgumentException("Unresolved");
            return resolved;
        }
    }

    protected static class Equation extends Resolvable {
        protected ValueHolder<Resolvable> one = new ValueHolder<>();
        protected ValueHolder<Resolvable> two = new ValueHolder<>();
        protected BiFunction<Double, Double, Double> function;

        @Override
        public void resolve() {
            assert one.object != null;

            one.object.resolve();
            if(two.object != null) two.object.resolve();
            else {
                this.resolved = one.object.getValue();
                return;
            }
            this.resolved = function.apply(one.object.getValue(), two.object.getValue());
        }
    }
}
