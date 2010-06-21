/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class AAlphaTargetColorAdjustment extends PTargetColorAdjustment
{
    private TTargetAlphaToken _targetAlphaToken_;
    private PExpression _expression_;

    public AAlphaTargetColorAdjustment()
    {
        // Constructor
    }

    public AAlphaTargetColorAdjustment(
        @SuppressWarnings("hiding") TTargetAlphaToken _targetAlphaToken_,
        @SuppressWarnings("hiding") PExpression _expression_)
    {
        // Constructor
        setTargetAlphaToken(_targetAlphaToken_);

        setExpression(_expression_);

    }

    @Override
    public Object clone()
    {
        return new AAlphaTargetColorAdjustment(
            cloneNode(this._targetAlphaToken_),
            cloneNode(this._expression_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAAlphaTargetColorAdjustment(this);
    }

    public TTargetAlphaToken getTargetAlphaToken()
    {
        return this._targetAlphaToken_;
    }

    public void setTargetAlphaToken(TTargetAlphaToken node)
    {
        if(this._targetAlphaToken_ != null)
        {
            this._targetAlphaToken_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._targetAlphaToken_ = node;
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
            + toString(this._targetAlphaToken_)
            + toString(this._expression_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._targetAlphaToken_ == child)
        {
            this._targetAlphaToken_ = null;
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
        if(this._targetAlphaToken_ == oldChild)
        {
            setTargetAlphaToken((TTargetAlphaToken) newChild);
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
