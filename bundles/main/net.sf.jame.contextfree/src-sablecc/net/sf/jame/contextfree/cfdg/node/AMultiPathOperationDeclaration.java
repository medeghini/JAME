/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class AMultiPathOperationDeclaration extends PPathOperationDeclaration
{
    private PMultiPathOperation _multiPathOperation_;

    public AMultiPathOperationDeclaration()
    {
        // Constructor
    }

    public AMultiPathOperationDeclaration(
        @SuppressWarnings("hiding") PMultiPathOperation _multiPathOperation_)
    {
        // Constructor
        setMultiPathOperation(_multiPathOperation_);

    }

    @Override
    public Object clone()
    {
        return new AMultiPathOperationDeclaration(
            cloneNode(this._multiPathOperation_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAMultiPathOperationDeclaration(this);
    }

    public PMultiPathOperation getMultiPathOperation()
    {
        return this._multiPathOperation_;
    }

    public void setMultiPathOperation(PMultiPathOperation node)
    {
        if(this._multiPathOperation_ != null)
        {
            this._multiPathOperation_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._multiPathOperation_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._multiPathOperation_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._multiPathOperation_ == child)
        {
            this._multiPathOperation_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._multiPathOperation_ == oldChild)
        {
            setMultiPathOperation((PMultiPathOperation) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
