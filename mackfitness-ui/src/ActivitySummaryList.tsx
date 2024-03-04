import React, { useEffect, useState } from 'react';
import axios from 'axios';
import {
    DataGrid,
    GridColDef,
    GridFilterModel,
    GridFilterItem,
} from '@mui/x-data-grid';
import ActivitySummary from './ActivitySummary';

function ActivitySummaryList() {
    const [activitySummaries, setActivitySummaries] = useState<ActivitySummary[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [filterColumn, setFilterColumn] = useState<string | undefined>("");
    const [filterValue, setFilterValue] = useState<string | undefined>("");
    const [paginationModel, setPaginationModel] = React.useState({
        pageSize: 25,
        page: 0,
    });
    const [totalActivities, setTotalActivities] = useState<number>(0);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get('http://localhost:8080/activity-summarries', {
                    params: {
                        page: paginationModel.page,
                        size: paginationModel.pageSize,
                        sortColumn: 'startDateLocal',
                        sortDirection: 'desc',
                        filterColumn,
                        filterValue,
                    },
                });
                setActivitySummaries(response.data.activitySummaryList);
                setTotalActivities(response.data.totalActivities);
            } catch (error) {
                console.error('Error fetching activity summaries:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, [filterColumn, filterValue, paginationModel]);

    const handleFilterChange = (filterModel: GridFilterModel) => {
        if (filterModel.items.length > 0) {
            const filterItem = filterModel.items[0] as GridFilterItem;
            setFilterColumn(filterItem.field);
            setFilterValue(filterItem.value);
        } else {
            setFilterColumn(undefined);
            setFilterValue(undefined);
        }
    };

    const columns: GridColDef[] = [
        //{ field: 'id', headerName: 'ID', width: 90 },
        { field: 'name', headerName: 'Name', width: 200 },
        { field: 'totalMiles', headerName: 'Distance (mi))', width: 150 },
        { field: 'movingTimeFormatted', headerName: 'Time', width: 125 },
        { field: 'avgPace', headerName: 'Pace', width: 125 },
        { field: 'elevationGainFeet', headerName: 'Elevation Gain', width: 125 },
        { field: 'type', headerName: 'Type', width: 125 },
        //{ field: 'sport_type', headerName: 'Sport Type', width: 150 },
        { field: 'startDateLocalFormatted', headerName: 'Start Date', width: 200 },
    ];

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div style={{ height: 800, width: 1050 }}>
            <DataGrid
                rows={activitySummaries}
                rowCount={totalActivities}
                columns={columns}
                pageSizeOptions={[10, 25, 50]}
                paginationMode="server"
                filterMode="server"
                sortingMode="server"
                paginationModel={paginationModel}
                onPaginationModelChange={setPaginationModel}
                onFilterModelChange={handleFilterChange}
                initialState={{
                    pagination: { paginationModel: paginationModel },
                }}
            />
        </div>
    );
};

export default ActivitySummaryList;
