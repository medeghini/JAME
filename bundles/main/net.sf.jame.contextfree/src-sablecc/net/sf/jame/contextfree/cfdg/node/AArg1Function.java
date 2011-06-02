/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.Analysis;

@SuppressWarnings("nls")
public final class AArg1Function extends PFunction
{
    private TFunctionArg1 _functionArg1_;
    private TLRbkt _lRbkt_;
    private PFirstExpression _firstExpression_;
    private TRRbkt _rRbkt_;

    public AArg1Function()
    {
        // Constructor
    }

    public AArg1Function(
        @SuppressWarnings("hiding") TFunctionArg1 _functionArg1_,
        @SuppressWarnings("hiding") TLRbkt _lRbkt_,
        @SuppressWarnings("hiding") PFirstExpression _firstExpression_,
        @SuppressWarnings("hiding") TRRbkt _rRbkt_)
    {
        // Constructor
        setFunctionArg1(_functionArg1_);

        setLRbkt(_lRbkt_);

        setFirstExpression(_firstExpression_);

        setRRbkt(_rRbkt_);

    }

    @Override
    public Object clone()
    {
        return new AArg1Function(
            cloneNode(this._functionArg1_),
            cloneNode(this._lRbkt_),
            cloneNode(this._firstExpression_),
            cloneNode(this._rRbkt_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAArg1Function(this);
    }

    public TFunctionArg1 getFunctionArg1()
    {
        return this._functionArg1_;
    }

    public void setFunctionArg1(TFunctionArg1 node)
    {
        if(this._functionArg1_ != null)
        {
            this._functionArg1_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._functionArg1_ = node;
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
            + toString(this._functionArg1_)
            + toString(this._lRbkt_)
            + toString(this._firstExpression_)
            + toString(this._rRbkt_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._functionArg1_ == child)
        {
            this._functionArg1_ = null;
            return;
        }

        if(this._lRbkt_ == child)
        {
            this._lRbkt_ = null;
            return;
        }

        if(this._firstExpression_ == child)
        {
            this._firstExpression_ = null;
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
        if(this._functionArg1_ == oldChild)
        {
            setFunctionArg1((TFunctionArg1) newChild);
            return;
        }

        if(this._lRbkt_ == oldChild)
        {
            setLRbkt((TLRbkt) newChild);
            return;
        }

        if(this._firstExpression_ == oldChild)
        {
            setFirstExpression((PFirstExpression) newChild);
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
