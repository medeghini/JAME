/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class TPathop extends Token
{
    public TPathop(String text)
    {
        setText(text);
    }

    public TPathop(String text, int line, int pos)
    {
        setText(text);
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TPathop(getText(), getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTPathop(this);
    }
}
