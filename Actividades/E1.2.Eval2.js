// Francisco Huelsz Prince
// A01019512

// P1
db.grades.aggregate([
    {
        $group: {
            _id: "$student_id"
        }
    },
    {
        $count: "students_total"
    }
])
// Ahora con map reduce because why not
db.grades.mapReduce(
    function(){
        emit(this.student_id, 1)
    },
    function(name, data){
        return 1;
    },
    {out:"res"}
)
db.res.count()

// P2
db.grades.aggregate([
    {
        $group: {
            _id: "$class_id"
        }
    },
    {
        $count: "classes_total"
    }
])
// Ahora con map reduce because why not
db.grades.mapReduce(
    function(){
        emit(this.class_id, 1)
    },
    function(name, data){
        return 1;
    },
    {out:"res"}
)
db.res.count()

// Cuantos alumnos por clase
db.grades.group(
    {
        key: {
            'class_id':1
        },
        reduce: function( curr, result ) {
            result.total += 1;
        },  
        initial: { total : 0 }
    }
)

// P3
db.grades.aggregate([
    {
        $project: {
            student_id: 1,
            class_id: 1,
            average: {
                $avg: "$scores.score"
            }
        }
    }
])

// P4
// la calificación mas baja de todo
db.grades.aggregate([
    {
        $unwind: "$scores"
    },
    {
        $sort:{
            "scores.score": 1
        }
    },
    {
        $limit: 1
    }
])
// la calificación promedio más baja
db.grades.aggregate([
    {
        $project: {
            student_id: 1,
            class_id: 1,
            average: {
                $avg: "$scores.score"
            }
        }
    },
    {
        $sort: {"average": 1}
    },
    {
        $limit: 1
    }
])

// P5
db.grades.mapReduce(
    function(){
        hw_amount = (this.scores.filter(function(grade){return grade.type === "homework"})).length
        emit(this.class_id, hw_amount)
    },
    function(name, data){
        return data.reduce(function (p, v) {
            return ( p > v ? p : v );
        });
    },
    {out:"res"}
)
db.res.find().sort({"value":-1}).pretty()

// P6
db.grades.aggregate([
    {
        $group: {
            _id: "$student_id",
            count: {$sum: 1}
        }
    },
    {
        $sort: {
            "count": -1
        }
    },
    {
        $limit: 1
    }
])
// Ahora con map reduce porque estaba abrrido
db.grades.mapReduce(
    function(){
        emit(this.student_id, 1);
    },
    function(name, data){
        return Array.sum(data);
    },
    {out:"res"}
)
db.res.find().sort({"value":-1}).pretty()

// P7
db.grades.aggregate([
    {
        $project: {
            student_id: 1,
            class_id: 1,
            average: {
                $avg: "$scores.score"
            }
        }
    },
    {
        $match:{
            average: {
                $lt: 70
            }
        }
    },
    {
        $group: {
            _id: "$class_id",
            reprobados: {$sum: 1}
        }
    },
    {
        $sort: {
            reprobados: -1
        }
    }
])

// Ahora con map reduce
db.grades.mapReduce(
    function(){
        grades = this.scores.map(function(x){ return x.score});
        avg = Array.sum(grades)/grades.length
        emit(this.class_id, avg < 70? 1 : 0);
    },
    function(name, data){
        return Array.sum(data);
    },
    {out:"res"}
)
db.res.find().sort({"value":-1}).pretty()

