package web.common.module.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Menu {

	private String id;
	private String parentId;
	private String name;
	private String tabId;
	private String iconClass;
	private String url;
	private Integer seqNo;
	private String status;

	private Integer depth;
	private boolean parent;

	private List<Menu> children;

	public Menu() {
		super();

		this.children = new ArrayList<Menu>();
		this.depth = 0;
		this.parent = false;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTabId() {
		return tabId;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

	public void setTabId(String tabId) {
		this.tabId = tabId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public boolean isParent() {
		return parent;
	}

	public void setParent(boolean parent) {
		this.parent = parent;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void buildChildren(List<Menu> list) {
		List<Menu> children = new ArrayList<Menu>();
		for (Menu menu : list) {
			if (menu.getParentId().equals(this.id)) {
				menu.setDepth(this.depth + 1);
				children.add(menu);
			}
		}
		this.children.addAll(children);
		if (this.children.size() > 0)
			this.setParent(true);
		else
			this.setParent(false);

		Collections.sort(this.children, new Comparator<Menu>() {
			@Override
			public int compare(Menu o1, Menu o2) {
				return o1.getSeqNo() - o2.getSeqNo();
			}
		});

		for (Menu menu : children) {
			menu.buildChildren(list);
		}
	}

}
