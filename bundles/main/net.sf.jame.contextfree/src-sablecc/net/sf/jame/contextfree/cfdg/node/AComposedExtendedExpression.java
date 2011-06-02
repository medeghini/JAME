/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.Analysis;

@SuppressWarnings("nls")
public final class AComposedExtendedExpression extends PExtendedExpression
{
    private PExtendedExpression _extendedExpression_;
    private POperator _operator_;
    private PExpression _expression_;

    public AComposedExtendedExpression()
    {
        // Constructor
    }

    public AComposedExtendedExpression(
        @SuppressWarnings("hiding") PExtendedExpression _extendedExpression_,
        @SuppressWarnings("hiding") POperator _operator_,
        @SuppressWarnings("hiding") PExpression _expression_)
    {
        // Constructor
        setExtendedExpression(_extendedExpression_);

        setOperator(_operator_);

        setExpression(_expression_);

    }

    @Override
    public Object clone()
    {
        return new AComposedExtendedExpression(
            cloneNode(this._extendedExpression_),
            cloneNode(this._operator_),
            cloneNode(this._expression_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAComposedExtendedExpression(this);
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

    public POperator getOperator()
    {
        return this._operator_;
    }

    public void setOperator(POperator node)
    {
        if(this._operator_ != null)
        {
            this._operator_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._operator_ = node;
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
            + toString(this._extendedExpression_)
            + toString(this._operator_)
            + toString(this._expression_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._extendedExpression_ == child)
        {
            this._extendedExpression_ = null;
            return;
        }

        if(this._operator_ == child)
        {
            this._operator_ = null;
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
        if(this._extendedExpression_ == oldChild)
        {
            setExtendedExpression((PExtendedExpression) newChild);
            return;
        }

        if(this._operator_ == oldChild)
        {
            setOperator((POperator) newChild);
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
