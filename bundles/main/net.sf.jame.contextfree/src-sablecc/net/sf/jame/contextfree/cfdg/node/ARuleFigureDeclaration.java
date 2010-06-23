/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class ARuleFigureDeclaration extends PFigureDeclaration
{
    private PRuleDeclaration _ruleDeclaration_;

    public ARuleFigureDeclaration()
    {
        // Constructor
    }

    public ARuleFigureDeclaration(
        @SuppressWarnings("hiding") PRuleDeclaration _ruleDeclaration_)
    {
        // Constructor
        setRuleDeclaration(_ruleDeclaration_);

    }

    @Override
    public Object clone()
    {
        return new ARuleFigureDeclaration(
            cloneNode(this._ruleDeclaration_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseARuleFigureDeclaration(this);
    }

    public PRuleDeclaration getRuleDeclaration()
    {
        return this._ruleDeclaration_;
    }

    public void setRuleDeclaration(PRuleDeclaration node)
    {
        if(this._ruleDeclaration_ != null)
        {
            this._ruleDeclaration_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._ruleDeclaration_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._ruleDeclaration_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._ruleDeclaration_ == child)
        {
            this._ruleDeclaration_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._ruleDeclaration_ == oldChild)
        {
            setRuleDeclaration((PRuleDeclaration) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
