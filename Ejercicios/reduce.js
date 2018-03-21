
db.createCollection("clients")
db.clients.insert(
    {
        name:"Paco",
        numProds:42,
        details:[
            {
                code:"p1",
                price:50
            },
            {
                code:"p2",
                price:40
            },
            {
                code:"p3",
                price:20
            },
            {
                code:"p4",
                price:30
            }
        ]
    }
)
db.clients.insert(
    {
        name:"Brayan",
        numProds:13,
        details:[
            {
                code:"p2",
                price:60
            },
            {
                code:"p3",
                price:70
            },
            {
                code:"p5",
                price:80
            },
            {
                code:"p6",
                price:90
            }
        ]
    }
)
db.clients.insert(
    {
        name:"Ricardo",
        numProds:24,
        details:[
            {
                code:"p5",
                price:80
            },
            {
                code:"p6",
                price:90
            }
        ]
    }
)
db.clients.insert(
    {
        name:"Ximena",
        numProds:50,
        details:[
            {
                code:"p1",
                price:50
            },
            {
                code:"p2",
                price:40
            },
            {
                code:"p3",
                price:20
            },
            {
                code:"p5",
                price:80
            },
            {
                code:"p6",
                price:90
            }
        ]
    }
)
db.clients.insert(
    {
        name:"Oliver",
        numProds:70,
        details:[
            {
                code:"p5",
                price:80
            },
            {
                code:"p7",
                price:100
            }
        ]
    }
)

var map = function(){
    emit(this.name, this.numProds);
};

var reduce = function(name, values) {
    var n = Array.sum(values);
    return {name, n};
};

var mapCosts = function(){
    this.details.forEach((value)=>{emit(value.code, value.price)});
}
var getCosts = function(prod, prices){
    return Array.sum(prices);
}

db.clients.mapReduce(mapCosts, getCosts, {out:"res"});

//mongoimport --db test --collection restaurants --drop --file primer-dataset.json
