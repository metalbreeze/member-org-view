var data = [
    {
        "group": "组asa1", 
        "tree": {
            "id": 1, 
            "name": "张三", 
            "uid": 1, 
            "children": [
                {
                    "id": 2, 
                    "name": "张4", 
                    "uid": 2, 
                    "children": [
                        {
                            "id": 6, 
                            "name": "张6", 
                            "uid": 6, 
                            "children": [ ]
                        }, 
                        {
                            "id": 7, 
                            "name": "张7", 
                            "uid": 7, 
                            "children": [ ]
                        }
                    ]
                }, 
                {
                    "id": 3, 
                    "name": "张5", 
                    "uid": 3, 
                    "children": [
                        {
                            "id": 8, 
                            "name": "张8", 
                            "uid": 8, 
                            "children": [ ]
                        }, 
                        {
                            "id": 9, 
                            "name": "张9", 
                            "uid": 9, 
                            "children": [ ]
                        }
                    ]
                }
            ]
        }
    }, 
    {
        "group": "组2asdf", 
        "tree": {
            "id": 11, 
            "name": "张1三", 
            "uid": 11, 
            "children": [
                {
                    "id": 12, 
                    "name": "张14", 
                    "uid": 12, 
                    "children": [
                        {
                            "id": 16, 
                            "name": "张16", 
                            "uid": 16, 
                            "children": [ ]
                        }, 
                        {
                            "id": 17, 
                            "name": "张17", 
                            "uid": 17, 
                            "children": [ ]
                        }
                    ]
                }, 
                {
                    "id": 13, 
                    "name": "张15", 
                    "uid": 13, 
                    "children": [
                        {
                            "id": 18, 
                            "name": "张18", 
                            "uid": 18, 
                            "children": [ ]
                        }, 
                        {
                            "id": 19, 
                            "name": "张19", 
                            "uid": 19, 
                            "children": [ ]
                        }
                    ]
                }
            ]
        }
    }
]

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

