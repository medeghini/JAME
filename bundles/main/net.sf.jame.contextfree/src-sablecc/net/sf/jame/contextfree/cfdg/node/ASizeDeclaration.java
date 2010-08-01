/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import java.util.*;
import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class ASizeDeclaration extends PSizeDeclaration
{
    private TSizeToken _sizeToken_;
    private TLCbkt _lCbkt_;
    private final LinkedList<PSizeAdjustment> _sizeAdjustment_ = new LinkedList<PSizeAdjustment>();
    private TRCbkt _rCbkt_;

    public ASizeDeclaration()
    {
        // Constructor
    }

    public ASizeDeclaration(
        @SuppressWarnings("hiding") TSizeToken _sizeToken_,
        @SuppressWarnings("hiding") TLCbkt _lCbkt_,
        @SuppressWarnings("hiding") List<PSizeAdjustment> _sizeAdjustment_,
        @SuppressWarnings("hiding") TRCbkt _rCbkt_)
    {
        // Constructor
        setSizeToken(_sizeToken_);

        setLCbkt(_lCbkt_);

        setSizeAdjustment(_sizeAdjustment_);

        setRCbkt(_rCbkt_);

    }

    @Override
    public Object clone()
    {
        return new ASizeDeclaration(
            cloneNode(this._sizeToken_),
            cloneNode(this._lCbkt_),
            cloneList(this._sizeAdjustment_),
            cloneNode(this._rCbkt_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASizeDeclaration(this);
    }

    public TSizeToken getSizeToken()
    {
        return this._sizeToken_;
    }

    public void setSizeToken(TSizeToken node)
    {
        if(this._sizeToken_ != null)
        {
            this._sizeToken_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._sizeToken_ = node;
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

    public LinkedList<PSizeAdjustment> getSizeAdjustment()
    {
        return this._sizeAdjustment_;
    }

    public void setSizeAdjustment(List<PSizeAdjustment> list)
    {
        this._sizeAdjustment_.clear();
        this._sizeAdjustment_.addAll(list);
        for(PSizeAdjustment e : list)
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
            + toString(this._sizeToken_)
            + toString(this._lCbkt_)
            + toString(this._sizeAdjustment_)
            + toString(this._rCbkt_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._sizeToken_ == child)
        {
            this._sizeToken_ = null;
            return;
        }

        if(this._lCbkt_ == child)
        {
            this._lCbkt_ = null;
            return;
        }

        if(this._sizeAdjustment_.remove(child))
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
        if(this._sizeToken_ == oldChild)
        {
            setSizeToken((TSizeToken) newChild);
            return;
        }

        if(this._lCbkt_ == oldChild)
        {
            setLCbkt((TLCbkt) newChild);
            return;
        }

        for(ListIterator<PSizeAdjustment> i = this._sizeAdjustment_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PSizeAdjustment) newChild);
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
