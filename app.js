function createMemberNode () {
    var node = document.createElement('div');
    node.className = 'member-card';
    node.innerHTML = '<p class="member-name">Group Name</p>' +
        '<div class="children"></div>'
    return node;
}

function createGroupNode () {
    var node = document.createElement('div');
    node.className = 'member-group';
    node.innerHTML = '<h1 class="group-name">Member Name</h1>' +
        '<div class="member-tree"></div>'
    return node;
}

/**
* @description Represents a Member
* @constructor
* @param  el - The DOM element which this member will insert into
* @param  data - The data of the member include `id`, `name`, `uid` and `children`
*/
var Member = function (el, data) {
    this.elParent = el;
    this.elThis = createMemberNode();
    this.id = data.id;
    this.name = data.name;
    this.uid = data.uid;

    this.children = data.children.map(function(el){
        return function(d){
            return new Member(el, d);
        };
    }(this.elThis.getElementsByClassName('children')[0]));
    console.log('create ' + this.name);
};

Member.prototype.render = function () {
    this.elParent.append(this.elThis);
    this.elThis.getElementsByClassName('member-name')[0].innerHTML = this.name;
    console.log(this);
    this.children.forEach(function(d){
        d.render();
    });
};

/**
* @description Represents a Member Group
* @constructor
* @param  el - The DOM element which groups will insert into
* @param  data - The data of the member include `group` and `tree`
*/
var MemberGroup = function (el, data) {
    this.elParent = el;
    this.elThis = createGroupNode();
    this.groupName = data.group;
    this.memberTree = new Member(this.elThis.getElementsByClassName('member-tree')[0], data.tree);
};

MemberGroup.prototype.render = function () {
    this.elParent.append(this.elThis);
    this.elThis.getElementsByClassName('group-name')[0].innerHTML = this.groupName;
    this.memberTree.render();
};

var elOrgContainer = document.getElementById('org-container');
var memberGroups = []

data.forEach(function(d){
    var memberGroup = new MemberGroup(elOrgContainer, d);
    memberGroups.push(memberGroup);
});

memberGroups.forEach(function(d){
    d.render();
});

