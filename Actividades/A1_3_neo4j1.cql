
// 1: R=102
match (p:Person)-[r:ACTED_IN]->() return p

// 2: R=8
match (p:Person)-[r:PRODUCED]->() return p

// 3: R=28
match (p:Person)-[r:DIRECTED]->() return p

// 4: R=32
match (m:Movie) return m, count(m)

// 5: R=Lilly Wachowski y Lana Wachowski con 2
match (p:Person)-[r:WROTE]->() return p,count(r) order by count(r) desc

// 6
MATCH p=()-[r:REVIEWED]->(m:Movie) RETURN m, avg(r.rating) AS rating order by rating desc limit 5

// 7
match t=(p:Person)-[r1:ACTED_IN]->(:Movie)<-[r2:PRODUCED]-(p) return p