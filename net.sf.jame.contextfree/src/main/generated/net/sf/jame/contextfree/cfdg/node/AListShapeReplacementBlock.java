/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import java.util.*;
import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class AListShapeReplacementBlock extends PShapeReplacementBlock
{
    private TLCbkt _lCbkt_;
    private final LinkedList<PShapeReplacementDeclaration> _shapeReplacementDeclaration_ = new LinkedList<PShapeReplacementDeclaration>();
    private TRCbkt _rCbkt_;

    public AListShapeReplacementBlock()
    {
        // Constructor
    }

    public AListShapeReplacementBlock(
        @SuppressWarnings("hiding") TLCbkt _lCbkt_,
        @SuppressWarnings("hiding") List<PShapeReplacementDeclaration> _shapeReplacementDeclaration_,
        @SuppressWarnings("hiding") TRCbkt _rCbkt_)
    {
        // Constructor
        setLCbkt(_lCbkt_);

        setShapeReplacementDeclaration(_shapeReplacementDeclaration_);

        setRCbkt(_rCbkt_);

    }

    @Override
    public Object clone()
    {
        return new AListShapeReplacementBlock(
            cloneNode(this._lCbkt_),
            cloneList(this._shapeReplacementDeclaration_),
            cloneNode(this._rCbkt_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAListShapeReplacementBlock(this);
    }

    public TLCbkt getLCbkt()
    {
        return this._lCbkt_;
    }

    public void setLCbkt(TLCbkt node)
    {
        if(this._lCbkt_ != null)
        {
            this._lCbkt_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._lCbkt_ = node;
    }

    public LinkedList<PShapeReplacementDeclaration> getShapeReplacementDeclaration()
    {
        return this._shapeReplacementDeclaration_;
    }

    public void setShapeReplacementDeclaration(List<PShapeReplacementDeclaration> list)
    {
        this._shapeReplacementDeclaration_.clear();
        this._shapeReplacementDeclaration_.addAll(list);
        for(PShapeReplacementDeclaration e : list)
        {
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
        }
    }

    public TRCbkt getRCbkt()
    {
        return this._rCbkt_;
    }

    public void setRCbkt(TRCbkt node)
    {
        if(this._rCbkt_ != null)
        {
            this._rCbkt_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._rCbkt_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._lCbkt_)
            + toString(this._shapeReplacementDeclaration_)
            + toString(this._rCbkt_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._lCbkt_ == child)
        {
            this._lCbkt_ = null;
            return;
        }

        if(this._shapeReplacementDeclaration_.remove(child))
        {
            return;
        }

        if(this._rCbkt_ == child)
        {
            this._rCbkt_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._lCbkt_ == oldChild)
        {
            setLCbkt((TLCbkt) newChild);
            return;
        }

        for(ListIterator<PShapeReplacementDeclaration> i = this._shapeReplacementDeclaration_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PShapeReplacementDeclaration) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        if(this._rCbkt_ == oldChild)
        {
            setRCbkt((TRCbkt) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
