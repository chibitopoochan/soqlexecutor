package com.gmail.chibitopoochan.soqlexec.soap.mock;

import com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerDescribeGlobalSObjectResultWrapper;

public class DescribeGlobalSObjectResultWrapperMock extends PartnerDescribeGlobalSObjectResultWrapper {
	private String name;
	private String label;
	private String keyPrefix;
	private boolean isSubtype;
	private boolean queryable;
	private boolean mruEnabled;
	private boolean mergeable;
	private boolean layoutable;
	private boolean searchable;
	private boolean retrieveable;
	private boolean replicateable;
	private boolean triggerable;
	private boolean undeletable;
	private boolean updateable;
	private boolean idEnabled;
	private boolean hasSubtypes;
	private boolean deprecatedAndHidden;
	private boolean feedEnabled;
	private boolean deletable;
	private boolean customSetting;
	private boolean custom;
	private boolean activateable;
	private String labelPlural;

	public DescribeGlobalSObjectResultWrapperMock(String name, String label, String keyPrefix) {
		this.name = name;
		this.label = label;
		this.keyPrefix = keyPrefix;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.DescribeGlobalSObjectResultWrapper#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.DescribeGlobalSObjectResultWrapper#getLabel()
	 */
	@Override
	public String getLabel() {
		return label;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.DescribeGlobalSObjectResultWrapper#getKeyPrefix()
	 */
	@Override
	public String getKeyPrefix() {
		return keyPrefix;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerDescribeGlobalSObjectResultWrapper#getLabelPlural()
	 */
	@Override
	public String getLabelPlural() {
		return labelPlural;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerDescribeGlobalSObjectResultWrapper#getActivateable()
	 */
	@Override
	public boolean getActivateable() {
		return activateable;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerDescribeGlobalSObjectResultWrapper#getCustom()
	 */
	@Override
	public boolean getCustom() {
		return custom;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerDescribeGlobalSObjectResultWrapper#getCustomSetting()
	 */
	@Override
	public boolean getCustomSetting() {
		return customSetting;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerDescribeGlobalSObjectResultWrapper#getDeletable()
	 */
	@Override
	public boolean getDeletable() {
		return deletable;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerDescribeGlobalSObjectResultWrapper#getDeprecatedAndHidden()
	 */
	@Override
	public boolean getDeprecatedAndHidden() {
		return deprecatedAndHidden;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerDescribeGlobalSObjectResultWrapper#getFeedEnabled()
	 */
	@Override
	public boolean getFeedEnabled() {
		return feedEnabled;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerDescribeGlobalSObjectResultWrapper#getHasSubtypes()
	 */
	@Override
	public boolean getHasSubtypes() {
		return hasSubtypes;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerDescribeGlobalSObjectResultWrapper#getIdEnabled()
	 */
	@Override
	public boolean getIdEnabled() {
		return idEnabled;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerDescribeGlobalSObjectResultWrapper#getIsSubtype()
	 */
	@Override
	public boolean getIsSubtype() {
		return isSubtype;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerDescribeGlobalSObjectResultWrapper#getLayoutable()
	 */
	@Override
	public boolean getLayoutable() {
		return layoutable;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerDescribeGlobalSObjectResultWrapper#getMergeable()
	 */
	@Override
	public boolean getMergeable() {
		return mergeable;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerDescribeGlobalSObjectResultWrapper#getMruEnabled()
	 */
	@Override
	public boolean getMruEnabled() {
		return mruEnabled;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerDescribeGlobalSObjectResultWrapper#getQueryable()
	 */
	@Override
	public boolean getQueryable() {
		return queryable;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerDescribeGlobalSObjectResultWrapper#getReplicateable()
	 */
	@Override
	public boolean getReplicateable() {
		return replicateable;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerDescribeGlobalSObjectResultWrapper#getRetrieveable()
	 */
	@Override
	public boolean getRetrieveable() {
		return retrieveable;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerDescribeGlobalSObjectResultWrapper#getSearchable()
	 */
	@Override
	public boolean getSearchable() {
		return searchable;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerDescribeGlobalSObjectResultWrapper#getTriggerable()
	 */
	@Override
	public boolean getTriggerable() {
		return triggerable;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerDescribeGlobalSObjectResultWrapper#getUndeletable()
	 */
	@Override
	public boolean getUndeletable() {
		return undeletable;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerDescribeGlobalSObjectResultWrapper#getUpdateable()
	 */
	@Override
	public boolean getUpdateable() {
		return updateable;
	}

	/**
	 * @param name セットする name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param label セットする label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @param keyPrefix セットする keyPrefix
	 */
	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	/**
	 * @param isSubtype セットする isSubtype
	 */
	public void setSubtype(boolean isSubtype) {
		this.isSubtype = isSubtype;
	}

	/**
	 * @param queryable セットする queryable
	 */
	public void setQueryable(boolean queryable) {
		this.queryable = queryable;
	}

	/**
	 * @param mruEnabled セットする mruEnabled
	 */
	public void setMruEnabled(boolean mruEnabled) {
		this.mruEnabled = mruEnabled;
	}

	/**
	 * @param mergeable セットする mergeable
	 */
	public void setMergeable(boolean mergeable) {
		this.mergeable = mergeable;
	}

	/**
	 * @param layoutable セットする layoutable
	 */
	public void setLayoutable(boolean layoutable) {
		this.layoutable = layoutable;
	}

	/**
	 * @param searchable セットする searchable
	 */
	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}

	/**
	 * @param retrieveable セットする retrieveable
	 */
	public void setRetrieveable(boolean retrieveable) {
		this.retrieveable = retrieveable;
	}

	/**
	 * @param replicateable セットする replicateable
	 */
	public void setReplicateable(boolean replicateable) {
		this.replicateable = replicateable;
	}

	/**
	 * @param triggerable セットする triggerable
	 */
	public void setTriggerable(boolean triggerable) {
		this.triggerable = triggerable;
	}

	/**
	 * @param undeletable セットする undeletable
	 */
	public void setUndeletable(boolean undeletable) {
		this.undeletable = undeletable;
	}

	/**
	 * @param updateable セットする updateable
	 */
	public void setUpdateable(boolean updateable) {
		this.updateable = updateable;
	}

	/**
	 * @param idEnabled セットする idEnabled
	 */
	public void setIdEnabled(boolean idEnabled) {
		this.idEnabled = idEnabled;
	}

	/**
	 * @param hasSubtypes セットする hasSubtypes
	 */
	public void setHasSubtypes(boolean hasSubtypes) {
		this.hasSubtypes = hasSubtypes;
	}

	/**
	 * @param deprecatedAndHidden セットする deprecatedAndHidden
	 */
	public void setDeprecatedAndHidden(boolean deprecatedAndHidden) {
		this.deprecatedAndHidden = deprecatedAndHidden;
	}

	/**
	 * @param feedEnabled セットする feedEnabled
	 */
	public void setFeedEnabled(boolean feedEnabled) {
		this.feedEnabled = feedEnabled;
	}

	/**
	 * @param deletable セットする deletable
	 */
	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}

	/**
	 * @param customSetting セットする customSetting
	 */
	public void setCustomSetting(boolean customSetting) {
		this.customSetting = customSetting;
	}

	/**
	 * @param custom セットする custom
	 */
	public void setCustom(boolean custom) {
		this.custom = custom;
	}

	/**
	 * @param activateable セットする activateable
	 */
	public void setActivateable(boolean activateable) {
		this.activateable = activateable;
	}

	/**
	 * @param labelPlural セットする labelPlural
	 */
	public void setLabelPlural(String labelPlural) {
		this.labelPlural = labelPlural;
	}

}
