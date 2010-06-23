/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class ANestedExtendedExpression extends PExtendedExpression
{
    private TLRbkt _lRbkt_;
    private PExtendedExpression _extendedExpression_;
    private TRRbkt _rRbkt_;

    public ANestedExtendedExpression()
    {
        // Constructor
    }

    public ANestedExtendedExpression(
        @SuppressWarnings("hiding") TLRbkt _lRbkt_,
        @SuppressWarnings("hiding") PExtendedExpression _extendedExpression_,
        @SuppressWarnings("hiding") TRRbkt _rRbkt_)
    {
        // Constructor
        setLRbkt(_lRbkt_);

        setExtendedExpression(_extendedExpression_);

        setRRbkt(_rRbkt_);

    }

    @Override
    public Object clone()
    {
        return new ANestedExtendedExpression(
            cloneNode(this._lRbkt_),
            cloneNode(this._extendedExpression_),
            cloneNode(this._rRbkt_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseANestedExtendedExpression(this);
    }

    public TLRbkt getLRbkt()
    {
        return this._lRbkt_;
    }

    public void setLRbkt(TLRbkt node)
    {
        if(this._lRbkt_ != null)
        {
            this._lRbkt_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._lRbkt_ = node;
    }

    public PExtendedExpression getExtendedExpression()
    {
        return this._extendedExpression_;
    }

    public void setExtendedExpression(PExtendedExpression node)
    {
        if(this._extendedExpression_ != null)
        {
            this._extendedExpression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._extendedExpression_ = node;
    }

    public TRRbkt getRRbkt()
    {
        return this._rRbkt_;
    }

    public void setRRbkt(TRRbkt node)
    {
        if(this._rRbkt_ != null)
        {
            this._rRbkt_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._rRbkt_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._lRbkt_)
            + toString(this._extendedExpression_)
            + toString(this._rRbkt_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._lRbkt_ == child)
        {
            this._lRbkt_ = null;
            return;
        }

        if(this._extendedExpression_ == child)
        {
            this._extendedExpression_ = null;
            return;
        }

        if(this._rRbkt_ == child)
        {
            this._rRbkt_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._lRbkt_ == oldChild)
        {
            setLRbkt((TLRbkt) newChild);
            return;
        }

        if(this._extendedExpression_ == oldChild)
        {
            setExtendedExpression((PExtendedExpression) newChild);
            return;
        }

        if(this._rRbkt_ == oldChild)
        {
            setRRbkt((TRRbkt) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
