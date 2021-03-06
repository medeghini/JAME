/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class AX2OperationParameter extends POperationParameter
{
    private TX2Token _x2Token_;
    private PExpression _expression_;

    public AX2OperationParameter()
    {
        // Constructor
    }

    public AX2OperationParameter(
        @SuppressWarnings("hiding") TX2Token _x2Token_,
        @SuppressWarnings("hiding") PExpression _expression_)
    {
        // Constructor
        setX2Token(_x2Token_);

        setExpression(_expression_);

    }

    @Override
    public Object clone()
    {
        return new AX2OperationParameter(
            cloneNode(this._x2Token_),
            cloneNode(this._expression_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAX2OperationParameter(this);
    }

    public TX2Token getX2Token()
    {
        return this._x2Token_;
    }

    public void setX2Token(TX2Token node)
    {
        if(this._x2Token_ != null)
        {
            this._x2Token_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._x2Token_ = node;
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
            + toString(this._x2Token_)
            + toString(this._expression_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._x2Token_ == child)
        {
            this._x2Token_ = null;
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
        if(this._x2Token_ == oldChild)
        {
            setX2Token((TX2Token) newChild);
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
