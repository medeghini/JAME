/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class TPath extends Token
{
    public TPath()
    {
        super.setText("path");
    }

    public TPath(int line, int pos)
    {
        super.setText("path");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TPath(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTPath(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TPath text.");
    }
}
