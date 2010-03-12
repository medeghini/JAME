/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class ASecondExpression extends PSecondExpression
{
    private PExpression2 _expression2_;

    public ASecondExpression()
    {
        // Constructor
    }

    public ASecondExpression(
        @SuppressWarnings("hiding") PExpression2 _expression2_)
    {
        // Constructor
        setExpression2(_expression2_);

    }

    @Override
    public Object clone()
    {
        return new ASecondExpression(
            cloneNode(this._expression2_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASecondExpression(this);
    }

    public PExpression2 getExpression2()
    {
        return this._expression2_;
    }

    public void setExpression2(PExpression2 node)
    {
        if(this._expression2_ != null)
        {
            this._expression2_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._expression2_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._expression2_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._expression2_ == child)
        {
            this._expression2_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._expression2_ == oldChild)
        {
            setExpression2((PExpression2) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
