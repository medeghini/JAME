/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class TBackground extends Token
{
    public TBackground()
    {
        super.setText("background");
    }

    public TBackground(int line, int pos)
    {
        super.setText("background");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TBackground(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTBackground(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TBackground text.");
    }
}
