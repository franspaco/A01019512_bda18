
// 1
db.restaurants.group(
    {
        key: {
            'address.zipcode':1
        },
        reduce: function( curr, result ) {
            result.total += 1;
        },
        initial: { total : 0 }
    }
)

// 2
db.restaurants.aggregate({
    $project: {
        name:"$name",
        count:{
            $size:"$grades"
        }
    }
});

// 3
db.restaurants.aggregate([
    {$unwind: "$grades"},
    {
        "$group": {
            "_id": "$name", 
            "total": {
                "$sum": "$grades.score"
            }
        }
    },
    {
        $project: {
            total:1
        }
    }
]);

// 4
db.restaurants.mapReduce(
    function() {
        var As = 0;
        var Bs = 0;
        var Cs = 0;
        this.grades.forEach(
            (value)=>{
                if(value.grade === "A"){
                    As = 1;
                }
                else if(value.grade === "B"){
                    Bs = 1;
                }
                else if(value.grade === "C"){
                    Cs = 1;
                }
            });
        emit("A", As);
        emit("B", Bs);
        emit("C", Cs);
    },
    function(name, count) {
        return Array.sum(count);
    },
    {out:"res"}
)

// 5 
db.restaurants.mapReduce(
    function(){
        emit(this.cuisine, 1)
    },
    function(name, count){
        return Array.sum(count);
    },
    {out:"res"}
);

