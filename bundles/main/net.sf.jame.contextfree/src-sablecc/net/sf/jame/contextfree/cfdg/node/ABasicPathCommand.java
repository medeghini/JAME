/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import java.util.*;
import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class ABasicPathCommand extends PPathCommand
{
    private TCommand _command_;
    private TLCbkt _lCbkt_;
    private final LinkedList<PCommandParameter> _commandParameter_ = new LinkedList<PCommandParameter>();
    private TRCbkt _rCbkt_;

    public ABasicPathCommand()
    {
        // Constructor
    }

    public ABasicPathCommand(
        @SuppressWarnings("hiding") TCommand _command_,
        @SuppressWarnings("hiding") TLCbkt _lCbkt_,
        @SuppressWarnings("hiding") List<PCommandParameter> _commandParameter_,
        @SuppressWarnings("hiding") TRCbkt _rCbkt_)
    {
        // Constructor
        setCommand(_command_);

        setLCbkt(_lCbkt_);

        setCommandParameter(_commandParameter_);

        setRCbkt(_rCbkt_);

    }

    @Override
    public Object clone()
    {
        return new ABasicPathCommand(
            cloneNode(this._command_),
            cloneNode(this._lCbkt_),
            cloneList(this._commandParameter_),
            cloneNode(this._rCbkt_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseABasicPathCommand(this);
    }

    public TCommand getCommand()
    {
        return this._command_;
    }

    public void setCommand(TCommand node)
    {
        if(this._command_ != null)
        {
            this._command_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._command_ = node;
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

    public LinkedList<PCommandParameter> getCommandParameter()
    {
        return this._commandParameter_;
    }

    public void setCommandParameter(List<PCommandParameter> list)
    {
        this._commandParameter_.clear();
        this._commandParameter_.addAll(list);
        for(PCommandParameter e : list)
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
            + toString(this._command_)
            + toString(this._lCbkt_)
            + toString(this._commandParameter_)
            + toString(this._rCbkt_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._command_ == child)
        {
            this._command_ = null;
            return;
        }

        if(this._lCbkt_ == child)
        {
            this._lCbkt_ = null;
            return;
        }

        if(this._commandParameter_.remove(child))
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
        if(this._command_ == oldChild)
        {
            setCommand((TCommand) newChild);
            return;
        }

        if(this._lCbkt_ == oldChild)
        {
            setLCbkt((TLCbkt) newChild);
            return;
        }

        for(ListIterator<PCommandParameter> i = this._commandParameter_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PCommandParameter) newChild);
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
