/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import java.util.*;
import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class AUnorderedPathReplacementDeclaration extends PPathReplacementDeclaration
{
    private TNumber _number_;
    private TStar _star_;
    private TLSbkt _lSbkt_;
    private final LinkedList<PPathAdjustment> _pathAdjustment_ = new LinkedList<PPathAdjustment>();
    private TRSbkt _rSbkt_;
    private PPathReplacementBlock _pathReplacementBlock_;

    public AUnorderedPathReplacementDeclaration()
    {
        // Constructor
    }

    public AUnorderedPathReplacementDeclaration(
        @SuppressWarnings("hiding") TNumber _number_,
        @SuppressWarnings("hiding") TStar _star_,
        @SuppressWarnings("hiding") TLSbkt _lSbkt_,
        @SuppressWarnings("hiding") List<PPathAdjustment> _pathAdjustment_,
        @SuppressWarnings("hiding") TRSbkt _rSbkt_,
        @SuppressWarnings("hiding") PPathReplacementBlock _pathReplacementBlock_)
    {
        // Constructor
        setNumber(_number_);

        setStar(_star_);

        setLSbkt(_lSbkt_);

        setPathAdjustment(_pathAdjustment_);

        setRSbkt(_rSbkt_);

        setPathReplacementBlock(_pathReplacementBlock_);

    }

    @Override
    public Object clone()
    {
        return new AUnorderedPathReplacementDeclaration(
            cloneNode(this._number_),
            cloneNode(this._star_),
            cloneNode(this._lSbkt_),
            cloneList(this._pathAdjustment_),
            cloneNode(this._rSbkt_),
            cloneNode(this._pathReplacementBlock_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAUnorderedPathReplacementDeclaration(this);
    }

    public TNumber getNumber()
    {
        return this._number_;
    }

    public void setNumber(TNumber node)
    {
        if(this._number_ != null)
        {
            this._number_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._number_ = node;
    }

    public TStar getStar()
    {
        return this._star_;
    }

    public void setStar(TStar node)
    {
        if(this._star_ != null)
        {
            this._star_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._star_ = node;
    }

    public TLSbkt getLSbkt()
    {
        return this._lSbkt_;
    }

    public void setLSbkt(TLSbkt node)
    {
        if(this._lSbkt_ != null)
        {
            this._lSbkt_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._lSbkt_ = node;
    }

    public LinkedList<PPathAdjustment> getPathAdjustment()
    {
        return this._pathAdjustment_;
    }

    public void setPathAdjustment(List<PPathAdjustment> list)
    {
        this._pathAdjustment_.clear();
        this._pathAdjustment_.addAll(list);
        for(PPathAdjustment e : list)
        {
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
        }
    }

    public TRSbkt getRSbkt()
    {
        return this._rSbkt_;
    }

    public void setRSbkt(TRSbkt node)
    {
        if(this._rSbkt_ != null)
        {
            this._rSbkt_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._rSbkt_ = node;
    }

    public PPathReplacementBlock getPathReplacementBlock()
    {
        return this._pathReplacementBlock_;
    }

    public void setPathReplacementBlock(PPathReplacementBlock node)
    {
        if(this._pathReplacementBlock_ != null)
        {
            this._pathReplacementBlock_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._pathReplacementBlock_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._number_)
            + toString(this._star_)
            + toString(this._lSbkt_)
            + toString(this._pathAdjustment_)
            + toString(this._rSbkt_)
            + toString(this._pathReplacementBlock_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._number_ == child)
        {
            this._number_ = null;
            return;
        }

        if(this._star_ == child)
        {
            this._star_ = null;
            return;
        }

        if(this._lSbkt_ == child)
        {
            this._lSbkt_ = null;
            return;
        }

        if(this._pathAdjustment_.remove(child))
        {
            return;
        }

        if(this._rSbkt_ == child)
        {
            this._rSbkt_ = null;
            return;
        }

        if(this._pathReplacementBlock_ == child)
        {
            this._pathReplacementBlock_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._number_ == oldChild)
        {
            setNumber((TNumber) newChild);
            return;
        }

        if(this._star_ == oldChild)
        {
            setStar((TStar) newChild);
            return;
        }

        if(this._lSbkt_ == oldChild)
        {
            setLSbkt((TLSbkt) newChild);
            return;
        }

        for(ListIterator<PPathAdjustment> i = this._pathAdjustment_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PPathAdjustment) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        if(this._rSbkt_ == oldChild)
        {
            setRSbkt((TRSbkt) newChild);
            return;
        }

        if(this._pathReplacementBlock_ == oldChild)
        {
            setPathReplacementBlock((PPathReplacementBlock) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
