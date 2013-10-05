/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class ABrightnessCurrentColorAdjustment extends PCurrentColorAdjustment
{
    private TBrightnessToken _brightnessToken_;
    private PExpression _expression_;
    private TBar _bar_;

    public ABrightnessCurrentColorAdjustment()
    {
        // Constructor
    }

    public ABrightnessCurrentColorAdjustment(
        @SuppressWarnings("hiding") TBrightnessToken _brightnessToken_,
        @SuppressWarnings("hiding") PExpression _expression_,
        @SuppressWarnings("hiding") TBar _bar_)
    {
        // Constructor
        setBrightnessToken(_brightnessToken_);

        setExpression(_expression_);

        setBar(_bar_);

    }

    @Override
    public Object clone()
    {
        return new ABrightnessCurrentColorAdjustment(
            cloneNode(this._brightnessToken_),
            cloneNode(this._expression_),
            cloneNode(this._bar_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseABrightnessCurrentColorAdjustment(this);
    }

    public TBrightnessToken getBrightnessToken()
    {
        return this._brightnessToken_;
    }

    public void setBrightnessToken(TBrightnessToken node)
    {
        if(this._brightnessToken_ != null)
        {
            this._brightnessToken_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._brightnessToken_ = node;
    }

    public PExpression getExpression()
    {
        return this._expression_;
    }

    public void setExpression(PExpression node)
    {
        if(this._expression_ != null)
        {
            this._expression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._expression_ = node;
    }

    public TBar getBar()
    {
        return this._bar_;
    }

    public void setBar(TBar node)
    {
        if(this._bar_ != null)
        {
            this._bar_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._bar_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._brightnessToken_)
            + toString(this._expression_)
            + toString(this._bar_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._brightnessToken_ == child)
        {
            this._brightnessToken_ = null;
            return;
        }

        if(this._expression_ == child)
        {
            this._expression_ = null;
            return;
        }

        if(this._bar_ == child)
        {
            this._bar_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._brightnessToken_ == oldChild)
        {
            setBrightnessToken((TBrightnessToken) newChild);
            return;
        }

        if(this._expression_ == oldChild)
        {
            setExpression((PExpression) newChild);
            return;
        }

        if(this._bar_ == oldChild)
        {
            setBar((TBar) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
