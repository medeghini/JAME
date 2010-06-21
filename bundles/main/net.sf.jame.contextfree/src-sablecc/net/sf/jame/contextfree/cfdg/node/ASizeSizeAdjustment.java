/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class ASizeSizeAdjustment extends PSizeAdjustment
{
    private TSizeToken _sizeToken_;
    private PFirstExpression _firstExpression_;
    private PSecondExpression _secondExpression_;

    public ASizeSizeAdjustment()
    {
        // Constructor
    }

    public ASizeSizeAdjustment(
        @SuppressWarnings("hiding") TSizeToken _sizeToken_,
        @SuppressWarnings("hiding") PFirstExpression _firstExpression_,
        @SuppressWarnings("hiding") PSecondExpression _secondExpression_)
    {
        // Constructor
        setSizeToken(_sizeToken_);

        setFirstExpression(_firstExpression_);

        setSecondExpression(_secondExpression_);

    }

    @Override
    public Object clone()
    {
        return new ASizeSizeAdjustment(
            cloneNode(this._sizeToken_),
            cloneNode(this._firstExpression_),
            cloneNode(this._secondExpression_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASizeSizeAdjustment(this);
    }

    public TSizeToken getSizeToken()
    {
        return this._sizeToken_;
    }

    public void setSizeToken(TSizeToken node)
    {
        if(this._sizeToken_ != null)
        {
            this._sizeToken_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._sizeToken_ = node;
    }

    public PFirstExpression getFirstExpression()
    {
        return this._firstExpression_;
    }

    public void setFirstExpression(PFirstExpression node)
    {
        if(this._firstExpression_ != null)
        {
            this._firstExpression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._firstExpression_ = node;
    }

    public PSecondExpression getSecondExpression()
    {
        return this._secondExpression_;
    }

    public void setSecondExpression(PSecondExpression node)
    {
        if(this._secondExpression_ != null)
        {
            this._secondExpression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._secondExpression_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._sizeToken_)
            + toString(this._firstExpression_)
            + toString(this._secondExpression_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._sizeToken_ == child)
        {
            this._sizeToken_ = null;
            return;
        }

        if(this._firstExpression_ == child)
        {
            this._firstExpression_ = null;
            return;
        }

        if(this._secondExpression_ == child)
        {
            this._secondExpression_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._sizeToken_ == oldChild)
        {
            setSizeToken((TSizeToken) newChild);
            return;
        }

        if(this._firstExpression_ == oldChild)
        {
            setFirstExpression((PFirstExpression) newChild);
            return;
        }

        if(this._secondExpression_ == oldChild)
        {
            setSecondExpression((PSecondExpression) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}