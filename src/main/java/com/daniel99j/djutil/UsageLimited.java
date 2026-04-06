package com.daniel99j.djutil;

public @interface UsageLimited {
    String intellijRule = """
            Rule:
            $Instance$.$MethodCall$($Parameter$)
            Script modifier:
            def m = __context__.getMethodExpression()?.resolve()
            return m != null && m.hasAnnotation("com.daniel99j.djutil.UsageLimited")
            """;
}
