/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class TStartshape extends Token
{
    public TStartshape()
    {
        super.setText("startshape");
    }

    public TStartshape(int line, int pos)
    {
        super.setText("startshape");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TStartshape(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTStartshape(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TStartshape text.");
    }
}
