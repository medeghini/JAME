/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class AZGeometryAdjustment extends PGeometryAdjustment
{
    private TZToken _zToken_;
    private PExpression _expression_;

    public AZGeometryAdjustment()
    {
        // Constructor
    }

    public AZGeometryAdjustment(
        @SuppressWarnings("hiding") TZToken _zToken_,
        @SuppressWarnings("hiding") PExpression _expression_)
    {
        // Constructor
        setZToken(_zToken_);

        setExpression(_expression_);

    }

    @Override
    public Object clone()
    {
        return new AZGeometryAdjustment(
            cloneNode(this._zToken_),
            cloneNode(this._expression_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAZGeometryAdjustment(this);
    }

    public TZToken getZToken()
    {
        return this._zToken_;
    }

    public void setZToken(TZToken node)
    {
        if(this._zToken_ != null)
        {
            this._zToken_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._zToken_ = node;
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
            + toString(this._zToken_)
            + toString(this._expression_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._zToken_ == child)
        {
            this._zToken_ = null;
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
        if(this._zToken_ == oldChild)
        {
            setZToken((TZToken) newChild);
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
