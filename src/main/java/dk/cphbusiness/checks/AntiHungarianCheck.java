package dk.cphbusiness.checks;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import java.util.regex.Pattern;

public class AntiHungarianCheck extends Check {
    private final Pattern pattern = Pattern.compile("m[A-Z0-9].*");
    private static final String CATCH_MESSAGE = "Don't use Hungarian notation!";
    
    private boolean detectHungarianNotation(String variableName) {
        return pattern.matcher(variableName).matches();
        }
    
    private boolean isFieldVariable(DetailAST ast) {
        return ast.getParent().getType() == TokenTypes.OBJBLOCK;
        }
    
    @Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.VARIABLE_DEF };
        }

    @Override
    public void visitToken(DetailAST ast) {
        String variableName = ast.findFirstToken(TokenTypes.IDENT).toString();
        if (isFieldVariable(ast) && detectHungarianNotation(variableName)) {
            log(ast.getLineNo(), CATCH_MESSAGE+variableName);
            }
        }
    
    }
