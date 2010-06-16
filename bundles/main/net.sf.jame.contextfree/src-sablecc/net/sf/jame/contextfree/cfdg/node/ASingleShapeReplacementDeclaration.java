/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class ASingleShapeReplacementDeclaration extends PShapeReplacementDeclaration
{
    private PSingleShapeReplacement _singleShapeReplacement_;

    public ASingleShapeReplacementDeclaration()
    {
        // Constructor
    }

    public ASingleShapeReplacementDeclaration(
        @SuppressWarnings("hiding") PSingleShapeReplacement _singleShapeReplacement_)
    {
        // Constructor
        setSingleShapeReplacement(_singleShapeReplacement_);

    }

    @Override
    public Object clone()
    {
        return new ASingleShapeReplacementDeclaration(
            cloneNode(this._singleShapeReplacement_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASingleShapeReplacementDeclaration(this);
    }

    public PSingleShapeReplacement getSingleShapeReplacement()
    {
        return this._singleShapeReplacement_;
    }

    public void setSingleShapeReplacement(PSingleShapeReplacement node)
    {
        if(this._singleShapeReplacement_ != null)
        {
            this._singleShapeReplacement_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._singleShapeReplacement_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._singleShapeReplacement_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._singleShapeReplacement_ == child)
        {
            this._singleShapeReplacement_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._singleShapeReplacement_ == oldChild)
        {
            setSingleShapeReplacement((PSingleShapeReplacement) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
