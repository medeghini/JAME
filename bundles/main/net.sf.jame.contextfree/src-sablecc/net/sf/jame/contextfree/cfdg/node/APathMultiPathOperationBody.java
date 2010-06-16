/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class APathMultiPathOperationBody extends PMultiPathOperationBody
{
    private PPathOperation _pathOperation_;

    public APathMultiPathOperationBody()
    {
        // Constructor
    }

    public APathMultiPathOperationBody(
        @SuppressWarnings("hiding") PPathOperation _pathOperation_)
    {
        // Constructor
        setPathOperation(_pathOperation_);

    }

    @Override
    public Object clone()
    {
        return new APathMultiPathOperationBody(
            cloneNode(this._pathOperation_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAPathMultiPathOperationBody(this);
    }

    public PPathOperation getPathOperation()
    {
        return this._pathOperation_;
    }

    public void setPathOperation(PPathOperation node)
    {
        if(this._pathOperation_ != null)
        {
            this._pathOperation_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._pathOperation_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._pathOperation_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._pathOperation_ == child)
        {
            this._pathOperation_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._pathOperation_ == oldChild)
        {
            setPathOperation((PPathOperation) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
