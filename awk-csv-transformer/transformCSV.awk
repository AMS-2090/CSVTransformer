#
# The purpose of this AWK script is to obtain the same result 
# of a csv file processing as the Java CSVTransformer does.
#
# author Arkadiusz Soltysiak
#

BEGIN {

    FS=";"
    OFS="|"

    file = "result.csv"
}

NR == 1 {

    # a new header
    header="'name'|'offerurl'|'price'|'published'|'description'"
    print header > file
}

NR != 1 && NF > 0 {

    # create a new field $2 (offerurl)
    col2 = rm_quotes($2) "?id=" rm_quotes($3)
    col2 = add_quotes(col2)
    $2 = col2

    # create a new field $3 (price)
    col3 = rm_quotes($4)
    match(col3, /[0-9]+([.,][0-9]{3})*([.,][0-9]{1,2})?/, arr)
    price = arr[0]
    #print "match: ", price

    price = gensub(/(.+?)([.,])([0-9]{1,2}$)/, "\\1#\\3", "g", price)
    gsub(/[.,]/, "", price)
    sub(/#/, ".", price)

    $3 = add_quotes(price)

    # create a new field $4 (published)
    col4 = rm_quotes($5)
    if (match(col4, /([0-9]{1,2})\.([0-9]{1,2})\.([0-9]{4})/, arr2)){
        col4 = norm_date(arr2[1], arr2[2], arr2[3])
    }
    else if (match(col4, /([0-9]{1,2})-([0-9]{1,2})-([0-9]{4})/, arr2)){
        col4 = norm_date(arr2[2], arr2[1], arr2[3])
    } else col4 = ""

    $4 = add_quotes(col4)

    gsub(/"/,"'")

    print >> file
}

END {
    

}

function rm_quotes(line) {
    return substr(line, 2, length(line)-2)
}

function add_quotes(line) {
    return "\""line"\""
}

function norm_date(day, month, year) {
    if (length(day) == 1)
        day = "0"day
    if (length(month) == 1)
        month = "0"month
    
    return day "."  month "."  year
}
