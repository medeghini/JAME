/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class TBrightnessToken extends Token
{
    public TBrightnessToken(String text)
    {
        setText(text);
    }

    public TBrightnessToken(String text, int line, int pos)
    {
        setText(text);
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TBrightnessToken(getText(), getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTBrightnessToken(this);
    }
}