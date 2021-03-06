/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class AYPathAdjustment extends PPathAdjustment
{
    private TYToken _yToken_;
    private PExpression _expression_;

    public AYPathAdjustment()
    {
        // Constructor
    }

    public AYPathAdjustment(
        @SuppressWarnings("hiding") TYToken _yToken_,
        @SuppressWarnings("hiding") PExpression _expression_)
    {
        // Constructor
        setYToken(_yToken_);

        setExpression(_expression_);

    }

    @Override
    public Object clone()
    {
        return new AYPathAdjustment(
            cloneNode(this._yToken_),
            cloneNode(this._expression_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAYPathAdjustment(this);
    }

    public TYToken getYToken()
    {
        return this._yToken_;
    }

    public void setYToken(TYToken node)
    {
        if(this._yToken_ != null)
        {
            this._yToken_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._yToken_ = node;
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
            + toString(this._yToken_)
            + toString(this._expression_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._yToken_ == child)
        {
            this._yToken_ = null;
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
        if(this._yToken_ == oldChild)
        {
            setYToken((TYToken) newChild);
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
