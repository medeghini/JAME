/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class AParametersOperationParameter extends POperationParameter
{
    private TParametersToken _parametersToken_;
    private TString _string_;

    public AParametersOperationParameter()
    {
        // Constructor
    }

    public AParametersOperationParameter(
        @SuppressWarnings("hiding") TParametersToken _parametersToken_,
        @SuppressWarnings("hiding") TString _string_)
    {
        // Constructor
        setParametersToken(_parametersToken_);

        setString(_string_);

    }

    @Override
    public Object clone()
    {
        return new AParametersOperationParameter(
            cloneNode(this._parametersToken_),
            cloneNode(this._string_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAParametersOperationParameter(this);
    }

    public TParametersToken getParametersToken()
    {
        return this._parametersToken_;
    }

    public void setParametersToken(TParametersToken node)
    {
        if(this._parametersToken_ != null)
        {
            this._parametersToken_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._parametersToken_ = node;
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

    @Override
    public String toString()
    {
        return ""
            + toString(this._parametersToken_)
            + toString(this._string_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._parametersToken_ == child)
        {
            this._parametersToken_ = null;
            return;
        }

        if(this._string_ == child)
        {
            this._string_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._parametersToken_ == oldChild)
        {
            setParametersToken((TParametersToken) newChild);
            return;
        }

        if(this._string_ == oldChild)
        {
            setString((TString) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
