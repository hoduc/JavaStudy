package basicjava.java17;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Java17Test {
    // example lifted from : https://openjdk.org/jeps/409
    // but implementation is provided by me
    sealed interface Expr
        permits ConstantExpr, PlusExpr, TimesExpr, NegExpr {
            int eval();
        }

    record ConstantExpr(int i) implements Expr {
        @Override
        public int eval() {
            return i;
        }
    }
    record PlusExpr(Expr a, Expr b) implements Expr {
        @Override
        public int eval() {
            return a.eval() + b.eval();
        }

    }

    record TimesExpr(Expr a, Expr b) implements Expr {
        @Override
        public int eval() {
            return a.eval() * b.eval();
        }
    }

    record NegExpr(Expr e) implements Expr {
        @Override
        public int eval() {
            return -e.eval();
        }
    }

    @Test
    void sealedClasses() {
        assertEquals(0, 
            // (-(1 + 2))*2 + 6
            new PlusExpr(
                new TimesExpr(
                    new NegExpr(
                        new PlusExpr(
                            new ConstantExpr(1),
                            new ConstantExpr(2)
                        )
                    ),
                    new ConstantExpr(2)
                ),
                new ConstantExpr(6)
            ).eval()
        );
    }
}
