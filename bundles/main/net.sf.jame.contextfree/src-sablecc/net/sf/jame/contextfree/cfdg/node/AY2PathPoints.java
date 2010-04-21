/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class AY2PathPoints extends PPathPoints
{
    private TY2Token _y2Token_;
    private PExpression _expression_;

    public AY2PathPoints()
    {
        // Constructor
    }

    public AY2PathPoints(
        @SuppressWarnings("hiding") TY2Token _y2Token_,
        @SuppressWarnings("hiding") PExpression _expression_)
    {
        // Constructor
        setY2Token(_y2Token_);

        setExpression(_expression_);

    }

    @Override
    public Object clone()
    {
        return new AY2PathPoints(
            cloneNode(this._y2Token_),
            cloneNode(this._expression_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAY2PathPoints(this);
    }

    public TY2Token getY2Token()
    {
        return this._y2Token_;
    }

    public void setY2Token(TY2Token node)
    {
        if(this._y2Token_ != null)
        {
            this._y2Token_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._y2Token_ = node;
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

    @Override
    public String toString()
    {
        return ""
            + toString(this._y2Token_)
            + toString(this._expression_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._y2Token_ == child)
        {
            this._y2Token_ = null;
            return;
        }

        if(this._expression_ == child)
        {
            this._expression_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._y2Token_ == oldChild)
        {
            setY2Token((TY2Token) newChild);
            return;
        }

        if(this._expression_ == oldChild)
        {
            setExpression((PExpression) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
