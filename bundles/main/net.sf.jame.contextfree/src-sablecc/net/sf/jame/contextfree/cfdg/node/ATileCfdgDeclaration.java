/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.Analysis;

@SuppressWarnings("nls")
public final class ATileCfdgDeclaration extends PCfdgDeclaration
{
    private PTileDeclaration _tileDeclaration_;

    public ATileCfdgDeclaration()
    {
        // Constructor
    }

    public ATileCfdgDeclaration(
        @SuppressWarnings("hiding") PTileDeclaration _tileDeclaration_)
    {
        // Constructor
        setTileDeclaration(_tileDeclaration_);

    }

    @Override
    public Object clone()
    {
        return new ATileCfdgDeclaration(
            cloneNode(this._tileDeclaration_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseATileCfdgDeclaration(this);
    }

    public PTileDeclaration getTileDeclaration()
    {
        return this._tileDeclaration_;
    }

    public void setTileDeclaration(PTileDeclaration node)
    {
        if(this._tileDeclaration_ != null)
        {
            this._tileDeclaration_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._tileDeclaration_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._tileDeclaration_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._tileDeclaration_ == child)
        {
            this._tileDeclaration_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._tileDeclaration_ == oldChild)
        {
            setTileDeclaration((PTileDeclaration) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
