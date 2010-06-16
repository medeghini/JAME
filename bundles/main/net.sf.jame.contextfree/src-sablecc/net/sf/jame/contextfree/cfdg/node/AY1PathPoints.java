/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class AY1PathPoints extends PPathPoints
{
    private TY1Token _y1Token_;
    private PExpression _expression_;

    public AY1PathPoints()
    {
        // Constructor
    }

    public AY1PathPoints(
        @SuppressWarnings("hiding") TY1Token _y1Token_,
        @SuppressWarnings("hiding") PExpression _expression_)
    {
        // Constructor
        setY1Token(_y1Token_);

        setExpression(_expression_);

    }

    @Override
    public Object clone()
    {
        return new AY1PathPoints(
            cloneNode(this._y1Token_),
            cloneNode(this._expression_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAY1PathPoints(this);
    }

    public TY1Token getY1Token()
    {
        return this._y1Token_;
    }

    public void setY1Token(TY1Token node)
    {
        if(this._y1Token_ != null)
        {
            this._y1Token_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._y1Token_ = node;
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
            + toString(this._y1Token_)
            + toString(this._expression_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._y1Token_ == child)
        {
            this._y1Token_ = null;
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
        if(this._y1Token_ == oldChild)
        {
            setY1Token((TY1Token) newChild);
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
