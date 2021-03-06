/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class ASaturationTargetColorAdjustment extends PTargetColorAdjustment
{
    private TTargetSaturationToken _targetSaturationToken_;
    private PExpression _expression_;

    public ASaturationTargetColorAdjustment()
    {
        // Constructor
    }

    public ASaturationTargetColorAdjustment(
        @SuppressWarnings("hiding") TTargetSaturationToken _targetSaturationToken_,
        @SuppressWarnings("hiding") PExpression _expression_)
    {
        // Constructor
        setTargetSaturationToken(_targetSaturationToken_);

        setExpression(_expression_);

    }

    @Override
    public Object clone()
    {
        return new ASaturationTargetColorAdjustment(
            cloneNode(this._targetSaturationToken_),
            cloneNode(this._expression_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASaturationTargetColorAdjustment(this);
    }

    public TTargetSaturationToken getTargetSaturationToken()
    {
        return this._targetSaturationToken_;
    }

    public void setTargetSaturationToken(TTargetSaturationToken node)
    {
        if(this._targetSaturationToken_ != null)
        {
            this._targetSaturationToken_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._targetSaturationToken_ = node;
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
            + toString(this._targetSaturationToken_)
            + toString(this._expression_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._targetSaturationToken_ == child)
        {
            this._targetSaturationToken_ = null;
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
        if(this._targetSaturationToken_ == oldChild)
        {
            setTargetSaturationToken((TTargetSaturationToken) newChild);
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
