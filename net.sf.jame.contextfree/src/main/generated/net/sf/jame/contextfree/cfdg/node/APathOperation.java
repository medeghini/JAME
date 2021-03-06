/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import java.util.*;
import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class APathOperation extends PPathOperation
{
    private TOperation _operation_;
    private TLCbkt _lCbkt_;
    private final LinkedList<POperationParameter> _operationParameter_ = new LinkedList<POperationParameter>();
    private TRCbkt _rCbkt_;

    public APathOperation()
    {
        // Constructor
    }

    public APathOperation(
        @SuppressWarnings("hiding") TOperation _operation_,
        @SuppressWarnings("hiding") TLCbkt _lCbkt_,
        @SuppressWarnings("hiding") List<POperationParameter> _operationParameter_,
        @SuppressWarnings("hiding") TRCbkt _rCbkt_)
    {
        // Constructor
        setOperation(_operation_);

        setLCbkt(_lCbkt_);

        setOperationParameter(_operationParameter_);

        setRCbkt(_rCbkt_);

    }

    @Override
    public Object clone()
    {
        return new APathOperation(
            cloneNode(this._operation_),
            cloneNode(this._lCbkt_),
            cloneList(this._operationParameter_),
            cloneNode(this._rCbkt_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAPathOperation(this);
    }

    public TOperation getOperation()
    {
        return this._operation_;
    }

    public void setOperation(TOperation node)
    {
        if(this._operation_ != null)
        {
            this._operation_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._operation_ = node;
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

    public LinkedList<POperationParameter> getOperationParameter()
    {
        return this._operationParameter_;
    }

    public void setOperationParameter(List<POperationParameter> list)
    {
        this._operationParameter_.clear();
        this._operationParameter_.addAll(list);
        for(POperationParameter e : list)
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
            + toString(this._operation_)
            + toString(this._lCbkt_)
            + toString(this._operationParameter_)
            + toString(this._rCbkt_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._operation_ == child)
        {
            this._operation_ = null;
            return;
        }

        if(this._lCbkt_ == child)
        {
            this._lCbkt_ = null;
            return;
        }

        if(this._operationParameter_.remove(child))
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
        if(this._operation_ == oldChild)
        {
            setOperation((TOperation) newChild);
            return;
        }

        if(this._lCbkt_ == oldChild)
        {
            setLCbkt((TLCbkt) newChild);
            return;
        }

        for(ListIterator<POperationParameter> i = this._operationParameter_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((POperationParameter) newChild);
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
