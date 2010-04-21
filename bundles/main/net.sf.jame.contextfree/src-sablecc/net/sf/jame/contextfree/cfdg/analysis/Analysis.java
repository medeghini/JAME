/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.analysis;

import net.sf.jame.contextfree.cfdg.node.*;

public interface Analysis extends Switch
{
    Object getIn(Node node);
    void setIn(Node node, Object o);
    Object getOut(Node node);
    void setOut(Node node, Object o);

    void caseStart(Start node);
    void caseACfdg(ACfdg node);
    void caseARuleFigureDeclaration(ARuleFigureDeclaration node);
    void caseAPathFigureDeclaration(APathFigureDeclaration node);
    void caseAStartshapeDeclaration(AStartshapeDeclaration node);
    void caseAIncludeDeclaration(AIncludeDeclaration node);
    void caseABackgroundDeclaration(ABackgroundDeclaration node);
    void caseADefaultTileDeclaration(ADefaultTileDeclaration node);
    void caseAOrderedTileDeclaration(AOrderedTileDeclaration node);
    void caseADefaultSizeDeclaration(ADefaultSizeDeclaration node);
    void caseAOrderedSizeDeclaration(AOrderedSizeDeclaration node);
    void caseARuleDeclaration(ARuleDeclaration node);
    void caseAPathDeclaration(APathDeclaration node);
    void caseAMultiShapeReplacementDeclaration(AMultiShapeReplacementDeclaration node);
    void caseASingleShapeReplacementDeclaration(ASingleShapeReplacementDeclaration node);
    void caseADefaultSingleShapeReplacement(ADefaultSingleShapeReplacement node);
    void caseAOrderedSingleShapeReplacement(AOrderedSingleShapeReplacement node);
    void caseAListShapeReplacement(AListShapeReplacement node);
    void caseAMultiShapeReplacement(AMultiShapeReplacement node);
    void caseASingleMultiShapeReplacementBody(ASingleMultiShapeReplacementBody node);
    void caseAListMultiShapeReplacementBody(AListMultiShapeReplacementBody node);
    void caseAStarOperator(AStarOperator node);
    void caseAPlusOperator(APlusOperator node);
    void caseAMinusOperator(AMinusOperator node);
    void caseASlashOperator(ASlashOperator node);
    void caseAArrowOperator(AArrowOperator node);
    void caseAMultiPathOperationDeclaration(AMultiPathOperationDeclaration node);
    void caseAPathPathOperationDeclaration(APathPathOperationDeclaration node);
    void caseADefaultSimplePathOperation(ADefaultSimplePathOperation node);
    void caseAOrderedSimplePathOperation(AOrderedSimplePathOperation node);
    void caseADefaultPathopPathOperation(ADefaultPathopPathOperation node);
    void caseAOrderedPathopPathOperation(AOrderedPathopPathOperation node);
    void caseASimplePathOperation(ASimplePathOperation node);
    void caseAPathopPathOperation(APathopPathOperation node);
    void caseAListPathOperation(AListPathOperation node);
    void caseAMultiPathOperation(AMultiPathOperation node);
    void caseAPathMultiPathOperationBody(APathMultiPathOperationBody node);
    void caseAListMultiPathOperationBody(AListMultiPathOperationBody node);
    void caseAXPathPoints(AXPathPoints node);
    void caseAYPathPoints(AYPathPoints node);
    void caseAX1PathPoints(AX1PathPoints node);
    void caseAY1PathPoints(AY1PathPoints node);
    void caseAX2PathPoints(AX2PathPoints node);
    void caseAY2PathPoints(AY2PathPoints node);
    void caseARxPathPoints(ARxPathPoints node);
    void caseARyPathPoints(ARyPathPoints node);
    void caseARotatePathPoints(ARotatePathPoints node);
    void caseAParametersPathPoints(AParametersPathPoints node);
    void caseAColorPathAdjustment(AColorPathAdjustment node);
    void caseAGeometryPathAdjustment(AGeometryPathAdjustment node);
    void caseAStrokePathAdjustment(AStrokePathAdjustment node);
    void caseAParametersPathAdjustment(AParametersPathAdjustment node);
    void caseABackgroundAdjustment(ABackgroundAdjustment node);
    void caseATileAdjustment(ATileAdjustment node);
    void caseAXSizeAdjustment(AXSizeAdjustment node);
    void caseAYSizeAdjustment(AYSizeAdjustment node);
    void caseASizeSizeAdjustment(ASizeSizeAdjustment node);
    void caseAColorShapeAdjustment(AColorShapeAdjustment node);
    void caseAGeometryShapeAdjustment(AGeometryShapeAdjustment node);
    void caseAZShapeAdjustment(AZShapeAdjustment node);
    void caseASize3ShapeAdjustment(ASize3ShapeAdjustment node);
    void caseASimpleColorAdjustment(ASimpleColorAdjustment node);
    void caseATargetColorAdjustment(ATargetColorAdjustment node);
    void caseAHueSimpleColorAdjustment(AHueSimpleColorAdjustment node);
    void caseABrightnessSimpleColorAdjustment(ABrightnessSimpleColorAdjustment node);
    void caseASaturationSimpleColorAdjustment(ASaturationSimpleColorAdjustment node);
    void caseAAlphaSimpleColorAdjustment(AAlphaSimpleColorAdjustment node);
    void caseAHueTargetColorAdjustment(AHueTargetColorAdjustment node);
    void caseABrightnessTargetColorAdjustment(ABrightnessTargetColorAdjustment node);
    void caseASaturationTargetColorAdjustment(ASaturationTargetColorAdjustment node);
    void caseAAlphaTargetColorAdjustment(AAlphaTargetColorAdjustment node);
    void caseAXGeometryAdjustment(AXGeometryAdjustment node);
    void caseAYGeometryAdjustment(AYGeometryAdjustment node);
    void caseASizeGeometryAdjustment(ASizeGeometryAdjustment node);
    void caseASize2GeometryAdjustment(ASize2GeometryAdjustment node);
    void caseAFlipGeometryAdjustment(AFlipGeometryAdjustment node);
    void caseASkewGeometryAdjustment(ASkewGeometryAdjustment node);
    void caseARotateGeometryAdjustment(ARotateGeometryAdjustment node);
    void caseANumberExpression(ANumberExpression node);
    void caseANestedExpression(ANestedExpression node);
    void caseAFunctionExpression(AFunctionExpression node);
    void caseANumberExpression2(ANumberExpression2 node);
    void caseAFunctionExpression2(AFunctionExpression2 node);
    void caseANestedExpression2(ANestedExpression2 node);
    void caseAComposedExpression2(AComposedExpression2 node);
    void caseAArg0Function(AArg0Function node);
    void caseAArg1Function(AArg1Function node);
    void caseAArg2Function(AArg2Function node);
    void caseAFirstExpression(AFirstExpression node);
    void caseASecondExpression(ASecondExpression node);
    void caseAThirdExpression(AThirdExpression node);

    void caseTBar(TBar node);
    void caseTStar(TStar node);
    void caseTPlus(TPlus node);
    void caseTMinus(TMinus node);
    void caseTSlash(TSlash node);
    void caseTArrow(TArrow node);
    void caseTComma(TComma node);
    void caseTLCbkt(TLCbkt node);
    void caseTRCbkt(TRCbkt node);
    void caseTLRbkt(TLRbkt node);
    void caseTRRbkt(TRRbkt node);
    void caseTLSbkt(TLSbkt node);
    void caseTRSbkt(TRSbkt node);
    void caseTStartshape(TStartshape node);
    void caseTBackground(TBackground node);
    void caseTInclude(TInclude node);
    void caseTTile(TTile node);
    void caseTSize(TSize node);
    void caseTRule(TRule node);
    void caseTPath(TPath node);
    void caseTShape(TShape node);
    void caseTParametersToken(TParametersToken node);
    void caseTSizeToken(TSizeToken node);
    void caseTHueToken(THueToken node);
    void caseTSaturationToken(TSaturationToken node);
    void caseTBrightnessToken(TBrightnessToken node);
    void caseTAlphaToken(TAlphaToken node);
    void caseTTargetHueToken(TTargetHueToken node);
    void caseTTargetSaturationToken(TTargetSaturationToken node);
    void caseTTargetBrightnessToken(TTargetBrightnessToken node);
    void caseTTargetAlphaToken(TTargetAlphaToken node);
    void caseTXToken(TXToken node);
    void caseTYToken(TYToken node);
    void caseTZToken(TZToken node);
    void caseTX1Token(TX1Token node);
    void caseTY1Token(TY1Token node);
    void caseTX2Token(TX2Token node);
    void caseTY2Token(TY2Token node);
    void caseTRxToken(TRxToken node);
    void caseTRyToken(TRyToken node);
    void caseTRotateToken(TRotateToken node);
    void caseTFlipToken(TFlipToken node);
    void caseTSkewToken(TSkewToken node);
    void caseTStrokewidthToken(TStrokewidthToken node);
    void caseTPathop(TPathop node);
    void caseTFilename(TFilename node);
    void caseTString(TString node);
    void caseTNumber(TNumber node);
    void caseTWhiteSpace(TWhiteSpace node);
    void caseTLineComment(TLineComment node);
    void caseTBlockComment(TBlockComment node);
    void caseEOF(EOF node);
}
