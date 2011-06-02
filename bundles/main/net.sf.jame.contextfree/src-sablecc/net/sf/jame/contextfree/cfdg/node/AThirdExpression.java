/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.Analysis;

@SuppressWarnings("nls")
public final class AThirdExpression extends PThirdExpression
{
    private PExtendedExpression _extendedExpression_;

    public AThirdExpression()
    {
        // Constructor
    }

    public AThirdExpression(
        @SuppressWarnings("hiding") PExtendedExpression _extendedExpression_)
    {
        // Constructor
        setExtendedExpression(_extendedExpression_);

    }

    @Override
    public Object clone()
    {
        return new AThirdExpression(
            cloneNode(this._extendedExpression_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAThirdExpression(this);
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

    @Override
    public String toString()
    {
        return ""
            + toString(this._extendedExpression_);
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

        throw new RuntimeException("Not a child.");
    }
}
