/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.Analysis;

@SuppressWarnings("nls")
public final class TStrokewidthToken extends Token
{
    public TStrokewidthToken()
    {
        super.setText("width");
    }

    public TStrokewidthToken(int line, int pos)
    {
        super.setText("width");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TStrokewidthToken(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTStrokewidthToken(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TStrokewidthToken text.");
    }
}
