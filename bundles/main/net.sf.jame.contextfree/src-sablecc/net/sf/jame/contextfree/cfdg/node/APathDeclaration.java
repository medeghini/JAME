/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import java.util.*;
import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class APathDeclaration extends PPathDeclaration
{
    private TPath _path_;
    private TString _string_;
    private TLCbkt _lCbkt_;
    private final LinkedList<PPathOperationDeclaration> _pathOperationDeclaration_ = new LinkedList<PPathOperationDeclaration>();
    private TRCbkt _rCbkt_;

    public APathDeclaration()
    {
        // Constructor
    }

    public APathDeclaration(
        @SuppressWarnings("hiding") TPath _path_,
        @SuppressWarnings("hiding") TString _string_,
        @SuppressWarnings("hiding") TLCbkt _lCbkt_,
        @SuppressWarnings("hiding") List<PPathOperationDeclaration> _pathOperationDeclaration_,
        @SuppressWarnings("hiding") TRCbkt _rCbkt_)
    {
        // Constructor
        setPath(_path_);

        setString(_string_);

        setLCbkt(_lCbkt_);

        setPathOperationDeclaration(_pathOperationDeclaration_);

        setRCbkt(_rCbkt_);

    }

    @Override
    public Object clone()
    {
        return new APathDeclaration(
            cloneNode(this._path_),
            cloneNode(this._string_),
            cloneNode(this._lCbkt_),
            cloneList(this._pathOperationDeclaration_),
            cloneNode(this._rCbkt_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAPathDeclaration(this);
    }

    public TPath getPath()
    {
        return this._path_;
    }

    public void setPath(TPath node)
    {
        if(this._path_ != null)
        {
            this._path_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._path_ = node;
    }

    public TString getString()
    {
        return this._string_;
    }

    public void setString(TString node)
    {
        if(this._string_ != null)
        {
            this._string_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._string_ = node;
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

    public LinkedList<PPathOperationDeclaration> getPathOperationDeclaration()
    {
        return this._pathOperationDeclaration_;
    }

    public void setPathOperationDeclaration(List<PPathOperationDeclaration> list)
    {
        this._pathOperationDeclaration_.clear();
        this._pathOperationDeclaration_.addAll(list);
        for(PPathOperationDeclaration e : list)
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
            + toString(this._path_)
            + toString(this._string_)
            + toString(this._lCbkt_)
            + toString(this._pathOperationDeclaration_)
            + toString(this._rCbkt_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._path_ == child)
        {
            this._path_ = null;
            return;
        }

        if(this._string_ == child)
        {
            this._string_ = null;
            return;
        }

        if(this._lCbkt_ == child)
        {
            this._lCbkt_ = null;
            return;
        }

        if(this._pathOperationDeclaration_.remove(child))
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
        if(this._path_ == oldChild)
        {
            setPath((TPath) newChild);
            return;
        }

        if(this._string_ == oldChild)
        {
            setString((TString) newChild);
            return;
        }

        if(this._lCbkt_ == oldChild)
        {
            setLCbkt((TLCbkt) newChild);
            return;
        }

        for(ListIterator<PPathOperationDeclaration> i = this._pathOperationDeclaration_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PPathOperationDeclaration) newChild);
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
