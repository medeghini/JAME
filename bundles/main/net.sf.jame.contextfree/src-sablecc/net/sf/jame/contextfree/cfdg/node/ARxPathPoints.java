/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class ARxPathPoints extends PPathPoints
{
    private TRxToken _rxToken_;
    private PExpression _expression_;

    public ARxPathPoints()
    {
        // Constructor
    }

    public ARxPathPoints(
        @SuppressWarnings("hiding") TRxToken _rxToken_,
        @SuppressWarnings("hiding") PExpression _expression_)
    {
        // Constructor
        setRxToken(_rxToken_);

        setExpression(_expression_);

    }

    @Override
    public Object clone()
    {
        return new ARxPathPoints(
            cloneNode(this._rxToken_),
            cloneNode(this._expression_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseARxPathPoints(this);
    }

    public TRxToken getRxToken()
    {
        return this._rxToken_;
    }

    public void setRxToken(TRxToken node)
    {
        if(this._rxToken_ != null)
        {
            this._rxToken_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._rxToken_ = node;
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
            + toString(this._rxToken_)
            + toString(this._expression_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._rxToken_ == child)
        {
            this._rxToken_ = null;
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
        if(this._rxToken_ == oldChild)
        {
            setRxToken((TRxToken) newChild);
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
