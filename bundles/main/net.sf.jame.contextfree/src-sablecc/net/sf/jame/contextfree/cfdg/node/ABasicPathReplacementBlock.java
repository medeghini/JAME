/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class ABasicPathReplacementBlock extends PPathReplacementBlock
{
    private PPathReplacement _pathReplacement_;

    public ABasicPathReplacementBlock()
    {
        // Constructor
    }

    public ABasicPathReplacementBlock(
        @SuppressWarnings("hiding") PPathReplacement _pathReplacement_)
    {
        // Constructor
        setPathReplacement(_pathReplacement_);

    }

    @Override
    public Object clone()
    {
        return new ABasicPathReplacementBlock(
            cloneNode(this._pathReplacement_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseABasicPathReplacementBlock(this);
    }

    public PPathReplacement getPathReplacement()
    {
        return this._pathReplacement_;
    }

    public void setPathReplacement(PPathReplacement node)
    {
        if(this._pathReplacement_ != null)
        {
            this._pathReplacement_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._pathReplacement_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._pathReplacement_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._pathReplacement_ == child)
        {
            this._pathReplacement_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._pathReplacement_ == oldChild)
        {
            setPathReplacement((PPathReplacement) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
