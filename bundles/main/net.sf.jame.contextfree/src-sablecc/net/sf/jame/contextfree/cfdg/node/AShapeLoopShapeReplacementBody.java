/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class AShapeLoopShapeReplacementBody extends PLoopShapeReplacementBody
{
    private PShapeReplacement _shapeReplacement_;

    public AShapeLoopShapeReplacementBody()
    {
        // Constructor
    }

    public AShapeLoopShapeReplacementBody(
        @SuppressWarnings("hiding") PShapeReplacement _shapeReplacement_)
    {
        // Constructor
        setShapeReplacement(_shapeReplacement_);

    }

    @Override
    public Object clone()
    {
        return new AShapeLoopShapeReplacementBody(
            cloneNode(this._shapeReplacement_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAShapeLoopShapeReplacementBody(this);
    }

    public PShapeReplacement getShapeReplacement()
    {
        return this._shapeReplacement_;
    }

    public void setShapeReplacement(PShapeReplacement node)
    {
        if(this._shapeReplacement_ != null)
        {
            this._shapeReplacement_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._shapeReplacement_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._shapeReplacement_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._shapeReplacement_ == child)
        {
            this._shapeReplacement_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._shapeReplacement_ == oldChild)
        {
            setShapeReplacement((PShapeReplacement) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
